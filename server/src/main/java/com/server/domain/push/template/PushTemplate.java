package com.server.domain.push.template;

import com.server.global.utils.CustomRandom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PushTemplate {
    private String token;

    @Builder.Default
    private String title = "[PliP]";

    @Builder.Default
    private String body = "테스트 메시지";

    @Builder.Default
    private String imageUrl;

    @Builder.Default
    private String url = "https://plip.netlify.app/";

    @Builder
    public PushTemplate(String token, String title, String body,
            String region, String imageUrl, String url) {
        this.token = token;

        if (region == null && imageUrl == null) {
            region = CustomRandom.getRandomRegion();
        }
        if (region != null) {
            this.imageUrl = "https://teamdev.shop/files/images?region=" + region;
        } else {
            this.imageUrl = imageUrl;
        }

    }
}
