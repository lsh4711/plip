package com.server.domain.push.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;
import com.server.domain.member.entity.Member;
import com.server.domain.push.entity.Push;
import com.server.domain.push.repository.PushRepository;
import com.server.domain.push.template.PushTemplate;
import com.server.domain.push.template.PushTemplateConstructor;
import com.server.domain.schedule.entity.Schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushService {
    private final PushRepository pushRepository;
    private final PushTemplateConstructor pushTemplateConstructor;

    public Push savePush(Push push) {
        String token = push.getPushToken();
        Member member = push.getMember();
        Push foundPush = member.getPush();

        if (foundPush != null) {
            foundPush.setPushToken(token);
            return pushRepository.save(foundPush);
        }
        pushRepository.save(push);
        sendWelcomeMessage(token, member);

        return push;
    }

    @Async
    public void sendPush(PushTemplate pushTemplate) {
        Message requestMessage = Message.builder()
                .setToken(pushTemplate.getToken())
                .setNotification(Notification.builder()
                        .setTitle(pushTemplate.getTitle())
                        .setBody(pushTemplate.getBody())
                        .setImage(pushTemplate.getImageUrl())
                        .build())
                .setWebpushConfig(WebpushConfig.builder()
                        .setFcmOptions(WebpushFcmOptions.withLink(pushTemplate.getUrl()))
                        .build())
                .build();

        try {
            FirebaseMessaging.getInstance().send(requestMessage);
        } catch (FirebaseMessagingException e) {
            log.error("### 푸시 에러: ", e.getMessage());
            // throw new CustomException(ExceptionCode.PUSH_FAILD);
        }

    }

    @Async
    public void sendWelcomeMessage(String token, Member member) {
        String nickname = member.getNickname();
        PushTemplate pushTemplate = pushTemplateConstructor
                .getWelcomeTemplate(token, nickname);

        sendPush(pushTemplate);
    }

    @Async
    public void sendPostScheduleMessage(Schedule schedule) {
        // Member
        Member member = schedule.getMember();
        Push push = member.getPush();

        if (push == null) {
            return;
        }

        PushTemplate pushTemplate = pushTemplateConstructor
                .getPostScheduleTemplate(schedule, member, push);

        sendPush(pushTemplate);
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
