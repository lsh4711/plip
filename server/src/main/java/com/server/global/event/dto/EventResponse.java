package com.server.global.event.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventResponse {
    private long ranking;
    private String nickname;
    private byte[] giftCodeImage;
    private boolean win;

    public void setWin(boolean win) {
        this.win = win;
    }
}
