package com.server.domain.place.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceResponse {
    private long placeId;
    private long scheduleId;
    private long schedulePlaceId;

    private long apiId;
    private String name;
    private String address;
    private String latitude;
    private String longitude;

    private int days;
    private int orders;
    private boolean bookmark; // 구현전까진 기본값으로 false
}
