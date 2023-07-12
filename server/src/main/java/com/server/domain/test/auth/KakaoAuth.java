package com.server.domain.test.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoAuth {
    @Value("${kakao.redirect-url}")
    private String redirecUrl;

    @Value("${kakao.api-key}")
    private String apiKey;

    private String tokenApiUrl = "https://kauth.kakao.com/oauth/token";
    private String messageApiUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

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

    public String sendMessage(String accessToken, String message) {
        String body = "{\"object_type\": \"text\", \"text\": \"" + message + "\", \"link\": {}}";
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
