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
import com.server.domain.push.entity.PushMessage;
import com.server.domain.push.repository.PushRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushService {
    private final PushRepository pushRepository;

    public Push savePush(Push push) {
        String token = push.getPushToken();
        Member member = push.getMember();
        Push foundPush = member.getPush();

        if (foundPush != null) {
            foundPush.setPushToken(token);
            return pushRepository.save(foundPush);
        }

        String nickname = member.getNickname();
        PushMessage pushMessage = PushMessage.builder()
                .token(token)
                .title(String.format("%s님의 가입을 환영합니다!", nickname))
                .body("PliP과 함께 여행 일정을 작성하러 가볼까요?")
                .build();

        pushRepository.save(push);
        sendPush(pushMessage);

        return push;
    }

    @Async
    public void sendPush(PushMessage pushMessage) {
        String token = pushMessage.getToken();
        String title = pushMessage.getTitle();
        String body = pushMessage.getBody();
        String imageUrl = pushMessage.getImageUrl();
        String url = pushMessage.getUrl();

        Message requestMessage = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .setImage(imageUrl)
                        .build())
                .setWebpushConfig(WebpushConfig.builder()
                        .setFcmOptions(WebpushFcmOptions.withLink(url))
                        .build())
                .build();

        try {
            FirebaseMessaging.getInstance().send(requestMessage);
        } catch (FirebaseMessagingException e) {
            log.error("### 푸시 에러", e.getMessage());
            // throw new CustomException(ExceptionCode.PUSH_FAILD);
        }

    }
}
