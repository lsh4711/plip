package com.server.global.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomRandom {
    private static final Random random = new Random();

    private static String serverUrl;

    @Value("${url.server}")
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    private static final String[] REGION_LIST = { // 임시
        "busan", "chungbuk", "chungnam", "daegu", "daejeon", "gangwon", "gwangju", "gyeongbuk", "gyeonggi", "gyeongnam",
        "incheon", "jeju", "jeonbuk", "jeonnam", "seoul", "ulsan"};

    private static final String[] TITLE_FORMAT_LIST = {
        "즐거운 %s 여행",
        "%s 여행 드가자~",
        "%s 여행 가보자~",
        "%s 여행 레츠고!"
    };

    public static String getRandomRegion() {
        int length = REGION_LIST.length;
        int idx = random.nextInt(length);
        String region = REGION_LIST[idx];

        return region;
    }

    public static String getRandomTitleFormat() {
        int length = TITLE_FORMAT_LIST.length;
        int idx = random.nextInt(length);
        String format = TITLE_FORMAT_LIST[idx];

        return format;
    }

    public static String getRandomRegionUrl() {
        return String.format("%s/files/images?region=%s", serverUrl, getRandomRegion());
    }

    public static String getCustomRegionUrl(String region) {
        return String.format("%s/files/images?region=%s", serverUrl, region);
    }
}
