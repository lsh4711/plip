package com.server.domain.place.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.dto.PlaceResponse;
import com.server.domain.place.entity.Place;
import com.server.domain.schedule.entity.SchedulePlace;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    List<Place> postDtosToPlaces(List<PlaceDto.Post> postDtos);

    // PlaceResponse placeToPlaceResponse(Place place);

    // 'SchedulePlace'가 아닌 'Place'의 Response로 구분
    @Mapping(source = "schedule.scheduleId", target = "scheduleId")
    @Mapping(source = "place.placeId", target = "placeId")
    @Mapping(source = "place.apiId", target = "apiId")
    @Mapping(source = "place.name", target = "name")
    @Mapping(source = "place.address", target = "address")
    @Mapping(source = "place.latitude", target = "latitude")
    @Mapping(source = "place.longitude", target = "longitude")
    PlaceResponse schedulePlaceToPlaceResponse(SchedulePlace schedulePlace);

    List<PlaceResponse> schedulePlacesToPlaceResponses(List<SchedulePlace> schedulePlaces);
}
