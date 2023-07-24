package com.server.global.test.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;
import com.server.domain.member.entity.Member;
import com.server.domain.member.repository.MemberRepository;
import com.server.domain.oauth.entity.KakaoToken;
import com.server.domain.oauth.service.KakaoApiService;
import com.server.domain.oauth.template.KakaoTemplateConstructor;
import com.server.domain.oauth.template.KakaoTemplateObject.Text;
import com.server.domain.record.service.StorageService;
import com.server.domain.schedule.entity.Schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController { // 테스트 용이므로 비즈니스 로직도 함께 있습니다.
    private final StorageService StorageService;

    private final MemberRepository memberRepository;
    private final KakaoApiService kakaoApiService;
    private final KakaoTemplateConstructor kakaoTemplateConstructor;

    @GetMapping
    public ModelAndView getTest() {
        return new ModelAndView("test.html");
    }

    @GetMapping("/delete")
    public ResponseEntity test() {
        for (int i = 0; i <= 15; i++) {
            for (int j = 0; j <= 15; j++) {
                StorageService.deleteImgs(j, i);
            }
        }

        return ResponseEntity.ok("삭제 완료");
    }

    @GetMapping("/send")
    public ResponseEntity kakaoTrigger() {
        Member member = memberRepository.findByEmail("dowkwlp@gmail.com").get();
        List<Schedule> schedules = member.getSchedules();
        Schedule schedule = schedules.get(0);
        KakaoToken kakaoToken = member.getKakaoToken();
        String accessToken = kakaoToken.getAccessToken();
        Text textTemplate = kakaoTemplateConstructor.getScheduledTemplate(member, schedule, 22);

        kakaoApiService.sendMessage(textTemplate, accessToken);

        return ResponseEntity.ok("완료");
    }

    @PostMapping("/push")
    public ResponseEntity sendPush(@RequestBody Map<String, String> body) {
        String registrationToken = body.get("token");

        Message message = Message.builder()
                .setToken(registrationToken)
                .setNotification(Notification.builder()
                        .setTitle("제목")
                        .setBody("내용")
                        .setImage("https://teamdev.shop/files/images/test?name=test")
                        .build())
                .setWebpushConfig(WebpushConfig.builder()
                        .setFcmOptions(WebpushFcmOptions.withLink("https://teamdev.shop/"))
                        .build())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("### 푸시 에러", e.getMessage());
            return ResponseEntity.internalServerError().body("푸시 알림 전송 실패");
        }

        return ResponseEntity.ok("푸시 알림 전송 성공");
    }
}
