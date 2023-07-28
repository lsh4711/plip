package com.server.domain.oauth.service;

import java.util.List;
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
import com.server.domain.member.entity.Member;
import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.oauth.repository.KakaoTokenRepository;
import com.server.domain.oauth.template.KakaoTemplate.Feed;
import com.server.domain.oauth.template.KakaoTemplateConstructor;
import com.server.domain.schedule.entity.Schedule;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoApiService {
    private final KakaoTokenRepository kakaoTokenRepository;

    private final KakaoTemplateConstructor kakaoTemplateConstructor;

    private final KakaoTokenOauthService kakaoTokenOauthService;

    @Value("${KAKAO_CLIENT_ID}")
    private String apiKey;
    @Value("${KAKAO_CLIENT_SECRET}")
    private String apiSecret;

    private final String messageApiUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
    private final String tokenRenewalApiUri = "https://kauth.kakao.com/oauth/token";
    private final String unlinkKakaoApiUri = "https://kapi.kakao.com/v1/user/unlink";

    private final Gson gson;

    @Async
    public void sendMessage(Object template, String accessToken) {
        String body = gson.toJson(template);

        String result = WebClient.create(messageApiUrl)
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
            .bodyToMono(String.class)
            .block(); // 메시지 전송
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
            /*.flatMap(tokenResponse -> {
                // 토큰 갱신 후 다시 메시지 보내기
                *//*String renewedAccessToken = tokenResponse.get("access_token").toString();
                sendMessage(template, renewedAccessToken);*//*
                return Mono.empty();
            })*/
            .block();
    }

    @Async
    public void sendWelcomeMessage(Member member, String accessToken) {
        Feed feedTemplate = kakaoTemplateConstructor.getWelcomeTemplate(member);

        sendMessage(feedTemplate, accessToken);
    }

    @Async
    public void sendPostScheduleMessage(Schedule schedule) {
        Member member = schedule.getMember();
        KakaoToken kakaoToken = member.getKakaoToken();

        if (kakaoToken == null) {
            return; // or throw CustomExcepion
        }

        String accessToken = kakaoToken.getAccessToken();
        Feed feedTemplate = kakaoTemplateConstructor
            .getPostScheduleTemplate(schedule, member);

        sendMessage(feedTemplate, accessToken);
    }

    // 이벤트용
    @Async
    public void sendEventMessage(Member member, KakaoToken kakaoToken, long giftId) {
        String nickname = member.getNickname();
        String accessToken = kakaoToken.getAccessToken();

        Feed feedTemplate = kakaoTemplateConstructor
            .getEventTemplate(nickname, giftId);

        sendMessage(feedTemplate, accessToken);
    }

    // 이벤트용
    @Async
    public void sendNoticeMessage(String title, String message) {
        List<KakaoToken> kakaoTokens = kakaoTokenRepository.findAll();

        for (KakaoToken kakaoToken : kakaoTokens) {
            Member member = kakaoToken.getMember();
            String nickname = member.getNickname();
            String accessToken = kakaoToken.getAccessToken();
            Feed feedTemplate = kakaoTemplateConstructor
                .getNoticeTemplate(nickname, title, message);
            sendMessage(feedTemplate, accessToken);
        }

    }

    public void unlinkKaKaoService(String accessToken) {
        WebClient.create(unlinkKakaoApiUri)
            .post()
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
