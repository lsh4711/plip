package com.server.domain.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class Body {
    @Builder
    public static class Feed {
        private String object_type = "feed";
        // private Object item_content;
        private Content content;
        // private Social social;
        // private String button_title = "";
        private Button[] buttons;
    }

    @Builder
    public static class Text {
        private String object_type = "text";
        private String text = "내용입니다.";
        private Link link = new Link();
    }

    public static class Location {
        private String object_type = "location";
        private String address = "경기 성남시 분당구 판교역로 235";
        private String address_title = "카카오판교오피스";
        private Content content = new Content();
    }

    // 공용

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
        private String title = "제목입니다.";
        private String description;
        private int image_width = 600;
        private int image_height = 400;
        private String image_url = "https://teamdev.shop:8000/files/images?region=seoul";
        private Link link = new Link();
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Link {
        private String web_url = "https://plip.netlify.app/";
        private String mobile_web_url = "https://plip.netlify.app/";
        // private String android_execution_params = "";
        // private String ios_execution_params = "";
    }

    public static class Social {

    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Button {
        private String title;
        private Link link;
    }
}
