package com.server.global.event.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventResponse {
    private long ranking;
    private String nickname;
    private String message;
    private byte[] giftCodeImage;

    public void setMessage(String message) {
        this.message = message;
    }
}
