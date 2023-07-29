package com.server.global.event.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventResponse {
    private long ranking;
    private String nickname;
    private byte[] giftCodeImage; // url 노출을 막고 요청 횟수를 줄임
    private boolean win;

    public void setWin(boolean win) {
        this.win = win;
    }
}
