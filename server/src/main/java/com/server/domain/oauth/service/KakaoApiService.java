package com.server.domain.oauth.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.oauth.repository.KakaoTokenRepository;
import com.server.domain.oauth.template.KakaoTemplateObject;
import com.server.domain.oauth.template.KakaoTemplateObject.Link;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoApiService {
    @Value("${KAKAO_CLIENT_ID}")
    private String apiKey;
    @Value("${KAKAO_CLIENT_SECRET}")
    private String apiSecret;
    private final KakaoTokenRepository kakaoTokenRepository;
    private final KakaoTokenOauthService kakaoTokenOauthService;

    private final String messageApiUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
    private final String tokenRenewalApiUri = "https://kauth.kakao.com/oauth/token";

    private final Gson gson;

    @Async
    public void sendMessage(Object template, String accessToken) {
        String body = gson.toJson(template);

        WebClient.create(messageApiUrl)
            .post()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Authorization", "Bearer " + accessToken)
            .body(BodyInserters.fromFormData("template_object", body))
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, // 4xx 에러인 경우
                clientResponse -> {
                    renewKakaoAccessTokenAndResend(template, accessToken);
                    return Mono.empty();
                })
            .bodyToMono(Map.class)
            .block(); // 메시지 전송

            /*.bodyToMono(String.class)
            .block();*/
    }

    private void renewKakaoAccessTokenAndResend(Object template, String accessToken) {
        KakaoToken kakaoToken = kakaoTokenRepository.findByAccessToken(accessToken)
            .orElseThrow(() -> new CustomException(ExceptionCode.ACCESS_TOKEN_NOT_FOUND));

        WebClient.create(tokenRenewalApiUri)
            .post()
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                .with("client_id", apiKey)
                .with("client_secret", apiSecret)
                .with("refresh_token", kakaoToken.getRefreshToken()))
            .retrieve()
            .bodyToMono(Map.class)
            .doOnNext(tokenResponse -> {
                log.info(tokenResponse.toString());
                String refreshToken = (String)Optional.ofNullable(tokenResponse.get("refresh_token"))
                    .orElseGet(kakaoToken::getRefreshToken);
                kakaoTokenOauthService.saveOrUpdateToken(tokenResponse.get("access_token").toString(),
                    refreshToken, kakaoToken.getMember());
            })
            .flatMap(tokenResponse -> {
                // 토큰 갱신 후 다시 메시지 보내기
                String renewedAccessToken = tokenResponse.get("access_token").toString();
                sendMessage(template, renewedAccessToken);
                return Mono.empty();
            })
            .block();
    }

    // test
    public String sendTextMessage(String accessToken, String message) {
        KakaoTemplateObject.Text bodyBuilder = KakaoTemplateObject.Text.builder()
            .object_type("text")
            .text(message)
            .link(new Link())
            .build();
        String body = gson.toJson(bodyBuilder);

        String result = WebClient.create(messageApiUrl)
            .post()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Authorization", "Bearer " + accessToken)
            .body(BodyInserters.fromFormData("template_object", body))
            .retrieve()
            .bodyToMono(String.class)
            .block();

        return message;
    }
}