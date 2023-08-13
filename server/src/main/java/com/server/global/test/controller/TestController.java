package com.server.global.test.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.server.domain.record.service.S3StorageService;
import com.server.domain.record.service.StorageService;
import com.server.global.batch.BatchScheduler;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final StorageService storageService;

    @Value("${url.server}")
    private String serverUrl;

    @GetMapping
    public ModelAndView getTest() {
        return new ModelAndView("test.html");
    }

    private final BatchScheduler batchScheduler;

    // @GetMapping("/job/{hour}")
    public String jobTest(@PathVariable long hour) {
        if (hour == 7) {
            batchScheduler.runJobAt7();
        } else if (hour == 22) {
            batchScheduler.runJobAt22();
        } else if (hour == 21) {
            batchScheduler.runJobAt21();
        }

        return "성공";
    }

    // @GetMapping("/delete")
    public ResponseEntity test(Authentication authentication) {
        if (!authentication.getName().equals("admin@naver.com")) {
            throw new CustomException(ExceptionCode.FORBIDDEN);
        }

        ((S3StorageService)storageService).resetRecordImageStorage();

        return ResponseEntity.ok("삭제 완료");
    }

    @GetMapping("/send")
    public ResponseEntity kakaoTrigger() {
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
                        .setImage(serverUrl + "/files/images/test?name=test")
                        .build())
                .setWebpushConfig(WebpushConfig.builder()
                        .setFcmOptions(WebpushFcmOptions.withLink(serverUrl))
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
