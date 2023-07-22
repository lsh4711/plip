package com.server.domain.schedule.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.server.domain.place.dto.PlaceResponse;
import com.server.domain.place.entity.Place;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;

@Mapper(componentModel = "spring")
public interface SchedulePlaceMapper {
    // 'SchedulePlace'가 아닌 'Place'의 Response로 분류
    @Mapping(source = "schedulePlace.schedule.scheduleId", target = "scheduleId")
    // @Mapping(source = "place.placeId", target = "placeId")
    // @Mapping(source = "place.apiId", target = "apiId")
    // @Mapping(source = "place.name", target = "name")
    // @Mapping(source = "place.address", target = "address")
    // @Mapping(source = "place.latitude", target = "latitude")
    // @Mapping(source = "place.longitude", target = "longitude")

    // @Mapping(source = "place.phone", target = "phone")
    @Mapping(source = "place.category.code", target = "categoryCode")
    @Mapping(source = "place.category.name", target = "categoryName")
    @Mapping(source = "place.days", target = "days", ignore = true)
    @Mapping(source = "place.orders", target = "orders", ignore = true)
    @Mapping(source = "place.schedulePlaceId", target = "schedulePlaceId", ignore = true)
    public abstract PlaceResponse schedulePlaceToPlaceResponse(SchedulePlace schedulePlace, Place place);

    default List<List<PlaceResponse>> schedulePlacesToPlaceResponseLists(List<SchedulePlace> schedulePlaces,
            Schedule schedule) {
        int period = schedule.getPeriod();
        List<List<PlaceResponse>> placeResponseLists = new ArrayList<>();

        // 최적화 가능
        for (SchedulePlace schedulePlace : schedulePlaces) {
            int days = schedulePlace.getDays();
            Place place = schedulePlace.getPlace();
            PlaceResponse placeResponse = schedulePlaceToPlaceResponse(schedulePlace, place);
            while (placeResponseLists.size() < days) {
                placeResponseLists.add(new ArrayList<>());
            }
            placeResponseLists.get(days - 1).add(placeResponse);
        }
        while (placeResponseLists.size() < period) {
            placeResponseLists.add(new ArrayList<>());
        }

        return placeResponseLists;
    }
}
