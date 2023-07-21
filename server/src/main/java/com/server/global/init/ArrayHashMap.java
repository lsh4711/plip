package com.server.global.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import lombok.Getter;

@Getter
public class ArrayHashMap implements Iterable<String[]> {
    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<String> values = new ArrayList<>();
    private HashSet<String> set = new HashSet<>();

    public ArrayHashMap(String entity) {
        if (entity.equalsIgnoreCase("category")) {
            LinkedHashMap<String, String> categoryMap = new LinkedHashMap<>();
            categoryMap.put("", "");
            categoryMap.put("MT1", "대형마트");
            categoryMap.put("CS2", "편의점");
            categoryMap.put("PS3", "어린이집, 유치원");
            categoryMap.put("SC4", "학교");
            categoryMap.put("AC5", "학원");
            categoryMap.put("PK6", "주차장");
            categoryMap.put("OL7", "주유소, 충전소");
            categoryMap.put("SW8", "지하철역");
            categoryMap.put("BK9", "은행");
            categoryMap.put("CT1", "문화시설");
            categoryMap.put("AG2", "중개업소");
            categoryMap.put("PO3", "공공기관");
            categoryMap.put("AT4", "관광명소");
            categoryMap.put("AD5", "숙박");
            categoryMap.put("FD6", "음식점");
            categoryMap.put("CE7", "카페");
            categoryMap.put("HP8", "병원");
            categoryMap.put("PM9", "약국");

            // Region
            for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
                String code = entry.getKey();
                String name = entry.getValue();
                keys.add(code);
                values.add(name);
                set.add(code);
            }
        } else if (entity.equalsIgnoreCase("region")) {
            LinkedHashMap<String, String> regionMap = new LinkedHashMap<>();
            regionMap.put("", "");
            regionMap.put("busan", "부산");
            regionMap.put("chungbuk", "충북");
            regionMap.put("chungnam", "충남");
            regionMap.put("daegu", "대구");
            regionMap.put("daejeon", "대전");
            regionMap.put("gangwon", "강원");
            regionMap.put("gwangju", "광주");
            regionMap.put("gyeongbuk", "경북");
            regionMap.put("gyeonggi", "경기");
            regionMap.put("gyeongnam", "경남");
            regionMap.put("incheon", "인천");
            regionMap.put("jeju", "제주");
            regionMap.put("jeonbuk", "전북");
            regionMap.put("jeonnam", "전남");
            regionMap.put("seoul", "서울");
            regionMap.put("ulsan", "울산");

            for (Map.Entry<String, String> entry : regionMap.entrySet()) {
                String engName = entry.getKey();
                String korName = entry.getValue();
                keys.add(engName);
                values.add(korName);
                set.add(engName);
            }
        }
    }

    public boolean put(String key, String value) {
        if (set.contains(key)) {
            return false;
        }
        keys.add(key);
        values.add(value);
        set.add(key);

        return true;
    }

    public String[] get(int index) {
        if (index >= size()) {
            return null;
        }

        String[] entry = {keys.get(index), values.get(index)};

        return entry;
    }

    public boolean remove(String key) {
        if (!set.contains(key)) {
            return false;
        }

        for (int i = 0; i < keys.size(); i++) {
            String foundKey = keys.get(i);
            if (foundKey.equals(key)) {
                keys.remove(i);
                values.remove(i);
                set.remove(key);
            }
        }

        return true;
    }

    public boolean remove(int idx) {
        if (idx >= size()) {
            return false;
        }

        String key = keys.remove(idx);
        values.remove(idx);
        set.remove(key);

        return true;
    }

    public int size() {
        return keys.size();
    }

    @Override
    public Iterator<String[]> iterator() {
        return new Iterator<String[]>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return (idx < keys.size() && keys.get(idx) != null);
            }

            @Override
            public String[] next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                String[] entry = {keys.get(idx), values.get(idx++)};

                return entry;
            }
        };
    }
}
