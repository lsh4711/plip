package com.server.global.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.server.global.event.dto.EventResponse;
import com.server.global.event.entity.Gift;

@Mapper(componentModel = "spring")
public interface GiftMapper {
    @Mapping(source = "giftId", target = "ranking")
    EventResponse giftToEventResponse(Gift gift);
}
