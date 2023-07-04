package com.server.domain.place.mapper;

import org.mapstruct.Mapper;

import com.server.domain.place.dto.PlaceResponse;
import com.server.domain.place.entity.Place;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    PlaceResponse placeToPlaceResponse(Place place);
}
