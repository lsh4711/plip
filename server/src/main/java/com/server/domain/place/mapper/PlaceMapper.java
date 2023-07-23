package com.server.domain.place.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.dto.PlaceResponse;
import com.server.domain.place.entity.Place;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    @Mapping(source = "category", target = "category.code")
    Place postDtoToPlace(PlaceDto.Post postDto);

    List<Place> postDtosToPlaces(List<PlaceDto.Post> postDtos);

    List<List<Place>> postDtoListsToPlaceLists(List<List<PlaceDto.Post>> postDtos);

    // PlaceResponse placeToPlaceResponse(Place place);

    // 'SchedulePlace'가 아닌 'Place'의 Response로 분류
    @Mapping(source = "schedule.scheduleId", target = "scheduleId")
    @Mapping(source = "place.placeId", target = "placeId")
    @Mapping(source = "place.apiId", target = "apiId")
    @Mapping(source = "place.name", target = "name")
    @Mapping(source = "place.address", target = "address")
    @Mapping(source = "place.latitude", target = "latitude")
    @Mapping(source = "place.longitude", target = "longitude")

    @Mapping(source = "place.phone", target = "phone")
    @Mapping(source = "place.category.code", target = "category")
    @Mapping(source = "place.category.name", target = "categoryName")
    PlaceResponse schedulePlaceToPlaceResponse(SchedulePlace schedulePlace);

    default List<List<PlaceResponse>> schedulePlacesToPlaceResponseLists(List<SchedulePlace> schedulePlaces,
        Schedule schedule) {
        int period = schedule.getPeriod();
        List<List<PlaceResponse>> placeResponseLists = new ArrayList<>();

        if (schedulePlaces == null || schedulePlaces.size() == 0) {
            for (int i = 0; i < period; i++) {
                placeResponseLists.add(new ArrayList<>());
            }
            return placeResponseLists;
        }

        // 최적화 가능
        for (SchedulePlace schedulePlace : schedulePlaces) {
            int days = schedulePlace.getDays();
            PlaceResponse placeResponse = schedulePlaceToPlaceResponse(schedulePlace);
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
