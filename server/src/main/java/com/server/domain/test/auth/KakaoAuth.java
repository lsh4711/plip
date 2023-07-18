package com.server.domain.test.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.server.domain.test.dto.Body;
import com.server.domain.test.dto.Body.Link;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoAuth {
    @Value("${kakao.redirect-url}")
    private String redirecUrl;

    @Value("${kakao.api-key}")
    private String apiKey;

    private String tokenApiUrl = "https://kauth.kakao.com/oauth/token";
    private String messageApiUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

    private final Gson gson;

    public Token requestTokens(String code) {
        Token tokens = WebClient.create(tokenApiUrl)
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("grant_type", "authorization_code")
                        .with("client_id", apiKey)
                        .with("redirect_url", redirecUrl)
                        .with("code", code))
                .retrieve()
                .bodyToMono(Token.class)
                .block();

        return tokens;
    }

    public Token refreshTokens() {

        return null;
    }

    // @Async
    public void sendMessage(Object template, String accessToken) {
        String body = gson.toJson(template);

        System.out.println(body);

        String result = WebClient.create(messageApiUrl)
                .post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Bearer " + accessToken)
                .body(BodyInserters
                        .fromFormData("template_object", body))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // test
    public String sendMessage(String accessToken, String message) {
        Body.Text bodyBuilder = Body.Text.builder()
                .object_type("text")
                .text(message)
                .link(new Link())
                .build();
        String body = gson.toJson(bodyBuilder);

        // String body = "{\"object_type\": \"text\", \"text\": \"" + message + "\", \"link\": {}}";
        // System.out.println(body);
        String result = WebClient.create(messageApiUrl)
                .post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Bearer " + accessToken)
                .body(BodyInserters
                        .fromFormData("template_object", body))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return message;
    }

    public WebClient getClient(String url) {
        // return WebClient.create(url).get();
        return null;
    }

    public WebClient postClient() {
        return null;
    }
}
