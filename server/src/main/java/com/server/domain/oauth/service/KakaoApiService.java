package com.server.domain.oauth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.server.domain.member.entity.Member;
import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.oauth.template.KakaoTemplate.Feed;
import com.server.domain.oauth.template.KakaoTemplateConstructor;
import com.server.domain.schedule.entity.Schedule;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoApiService {
    private final KakaoTemplateConstructor kakaoTemplateConstructor;

    @Value("${kakao.api-key}")
    private String apiKey;

    private final String messageApiUrl = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

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
}
