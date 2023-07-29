package com.server.domain.place.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceResponse {
    private long placeId;
    private long scheduleId;
    private long schedulePlaceId;

    // Place
    private long apiId;
    private String name;
    private String address;
    private String phone;
    private String latitude;
    private String longitude;
    private String category;
    private String categoryName;

    // SchedulePlace
    private Integer days;
    private Integer orders;
    // private Boolean bookmark;
}
