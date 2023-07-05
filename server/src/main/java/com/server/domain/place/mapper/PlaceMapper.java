package com.server.domain.place.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.dto.PlaceResponse;
import com.server.domain.place.entity.Place;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    List<Place> placeDtosToPlaces(List<PlaceDto.Post> placeDtos);

    PlaceResponse placeToPlaceResponse(Place place);
}
