package com.server.domain.oauth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.server.domain.oauth.template.KakaoTemplateObject;
import com.server.domain.oauth.template.KakaoTemplateObject.Link;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoApiService {
    @Value("${kakao.api-key}")
    private String apiKey;

    private String messageApiUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

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
                .bodyToMono(String.class)
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
