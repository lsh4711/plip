package com.server.domain.test.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Notification {
    private String region;
    private String title;
    private String content;

    // 알림 보내는 메서드
    // @Async
    // private void sendNotifications(Schedule schedule, Long memberId, LocalDate startDate, LocalDate endDate) {
    //     Notification notification = createNotificationFromSchedule(schedule);
    //     LocalTime sendTime1 = LocalTime.of(16, 0);
    //     LocalTime sendTime2 = LocalTime.of(16, 1);
    //
    //     ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
    //     LocalDateTime currentTime = LocalDateTime.now();
    //
    //     LocalDateTime startDateTime = LocalDateTime.of(startDate, sendTime1);
    //     LocalDateTime endDateTime = LocalDateTime.of(endDate, sendTime2);
    //
    //     if (startDateTime.isAfter(currentTime)) {
    //         Duration initialDelay = Duration.between(currentTime, startDateTime);
    //
    //         executorService.schedule(() -> {
    //             // 알림 전송 작업
    //             SseEmitter emitter = emitterMap.get(memberId);
    //             try {
    //                 // emitter.send(notification, MediaType.APPLICATION_JSON);
    //                 emitter.send(SseEmitter.event().name("sse").data(notification));
    //                 System.out.println("StartDate Notification sent to memberId: " + memberId);
    //             } catch (Exception e) {
    //                 emitter.completeWithError(e);
    //             }
    //         }, initialDelay.toMillis(), TimeUnit.MILLISECONDS);
    //     }
    //
    //     if (endDateTime.isAfter(currentTime)) {
    //         Duration delay = Duration.between(currentTime, endDateTime);
    //
    //         executorService.schedule(() -> {
    //             // 알림 전송 작업
    //             SseEmitter emitter = emitterMap.get(memberId);
    //             System.out.println(emitter);
    //             try {
    //                 // emitter.send(notification, MediaType.APPLICATION_JSON);
    //                 emitter.send(SseEmitter.event().name("sse").data(notification));
    //                 System.out.println("EndDate Notification sent to memberId: " + memberId);
    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //                 emitter.completeWithError(e);
    //             } finally {
    //                 emitter.complete();
    //             }
    //         }, delay.toMillis(), TimeUnit.MILLISECONDS);
    //     }
    // }
    //
    // //알림 생성
    // private Notification createNotificationFromSchedule(Schedule schedule) {
    //     Notification notification = new Notification();
    //
    //     // 필요한 알림 필드 설정
    //     notification.setRegion(schedule.getRegion());
    //     notification.setTitle(schedule.getTitle());
    //     notification.setContent(schedule.getContent());
    //
    //
    //     return notification;
    // }
}
