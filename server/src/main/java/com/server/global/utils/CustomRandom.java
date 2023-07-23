package com.server.global.utils;

import java.util.Random;

public class CustomRandom {
    private static final String[] REGION_LIST = { // 임시
        "busan", "chungbuk", "chungnam", "daegu", "daejeon", "gangwon", "gwangju", "gyeongbuk", "gyeonggi", "gyeongnam",
        "incheon", "jeju", "jeonbuk", "jeonnam", "seoul", "ulsan"};

    public static String getRandomRegion() {
        Random random = new Random();
        int idx = random.nextInt(16);
        String region = REGION_LIST[idx];

        return region;
    }
}
