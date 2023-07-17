package com.server.domain.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class Body {
    public static class Text {
        private String object_type = "text";
        private String text = "경기 성남시 분당구 판교역로 235";
        private Link link = new Link();
    }

    public static class Location {
        private String object_type = "location";
        private String address = "경기 성남시 분당구 판교역로 235";
        private String address_title = "카카오판교오피스";
        private Content content = new Content();
    }

    // 공용

    public static class Content {
        private String title = "제목입니다.";
        private int image_width = 167;
        private int image_height = 200;
        private String image_url = "https://cdn.discordapp.com/attachments/1121434466871676948/1130436086041157696/logo.png";
        private Link link = new Link();
    }

    public static class Link {
        private String web_url = "https://plip.netlify.app/";
        private String mobile_web_url = "https://plip.netlify.app/";
        // private String android_execution_params = "";
        // private String ios_execution_params = "";
    }
}
