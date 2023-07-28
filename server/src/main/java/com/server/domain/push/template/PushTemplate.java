package com.server.domain.push.template;

import com.server.global.utils.CustomRandom;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PushTemplate {
    private String token;

    @Builder.Default
    private String title = "[PliP]";

    @Builder.Default
    private String body = "테스트 메시지";

    @Builder.Default
    private String imageUrl = CustomRandom.getRandomRegionUrl();

    @Builder.Default
    private String url = "https://plip.netlify.app/";
}
