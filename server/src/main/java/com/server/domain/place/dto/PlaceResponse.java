package com.server.domain.place.dto;

import lombok.Getter;

@Getter
public class PlaceResponse {
    private long placeId;
    private long apiId;
    private String name;
    private String address;
    private String latitude;
    private String longitude;

    private int day;
    private int order;
    private boolean bookmark;
}
