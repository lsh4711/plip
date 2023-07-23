package com.server.domain.push.service;

import org.springframework.stereotype.Service;

import com.server.domain.push.entity.Push;
import com.server.domain.push.repository.PushRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushService {
    private final PushRepository pushRepository;

    public Push savePush(Push push) {
        Push savedPush = pushRepository.save(push);

        return savedPush;
    }

    // public String sendPush(String token) {
    //     Message message = Message.builder()
    //             .setToken(token)
    //             .setNotification(Notification.builder()
    //                     .setTitle("제목")
    //                     .setBody("내용")
    //                     .setImage("https://teamdev.shop/files/images/test?name=test")
    //                     .build())
    //             .setWebpushConfig(WebpushConfig.builder()
    //                     .setFcmOptions(WebpushFcmOptions.withLink("https://teamdev.shop/"))
    //                     .build())
    //             .build();

    //     try {
    //         String response = FirebaseMessaging.getInstance().send(message);
    //     } catch (FirebaseMessagingException e) {
    //         log.error("### 푸시 에러", e.getMessage());
    //         ExceptionCode
    //         return ResponseEntity.internalServerError().body("푸시 알림 전송 실패");
    //     }

    //     return ResponseEntity.ok("푸시 알림 전송 성공");
    // }
}
