package com.server.domain.push.entity;

import com.server.global.utils.CustomRandom;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PushMessage {
    private String token;
    private String title;
    private String body;
    private String imageUrl;
    private String url;

    @Builder
    public PushMessage(String token, String title, String body,
            String region, String imageUrl, String url) {
        this.token = token;

        if (title == null) {
            title = "[PliP]";
        }
        this.title = title;

        if (body == null) {
            body = "테스트 메시지";
        }
        this.body = body;

        if (region == null && imageUrl == null) {
            region = CustomRandom.getRandomRegion();
        }
        if (region != null) {
            this.imageUrl = "https://teamdev.shop/files/images?region=" + region;
        } else {
            this.imageUrl = imageUrl;
        }

        if (url == null) {
            url = "https://plip.netlify.app/";
        }
        this.url = url;
    }
}
