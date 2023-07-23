package com.server.domain.push.entity;

import lombok.Getter;

@Getter
public class PushMessage {
    private String token;
    private String title;
    private String body;
    private String region;
    private String imageUrl;
    private String url;

    // public 
}
