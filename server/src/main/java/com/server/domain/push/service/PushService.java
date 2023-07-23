package com.server.domain.push.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.server.domain.push.entity.PushMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
// @RequiredArgsConstructor
public class PushService {
    // private final PushRepository pushRepository;

    // public Push savePush(Push push) {
    //     return pushRepository.save(push);
    // }

    @Async
    public void sendPush(PushMessage pushMessage) {
        // if (region == null) {
        //     region = CustomRandom.getRandomRegion();
        // }
        // Message requestMessage = Message.builder()
        //         .setToken(token)
        //         .setNotification(Notification.builder()
        //                 .setTitle("PliP")
        //                 .setBody(body)
        //                 .setImage("https://teamdev.shop/files/images?region=" + region)
        //                 .build())
        //         .setWebpushConfig(WebpushConfig.builder()
        //                 .setFcmOptions(WebpushFcmOptions.withLink("https://plip.netlify.app/"))
        //                 .build())
        //         .build();

        // try {
        //     FirebaseMessaging.getInstance().send(requestMessage);
        // } catch (FirebaseMessagingException e) {
        //     log.error("### 푸시 에러", e.getMessage());
        //     // throw new CustomException(ExceptionCode.PUSH_FAILD);
        // }

    }
}
