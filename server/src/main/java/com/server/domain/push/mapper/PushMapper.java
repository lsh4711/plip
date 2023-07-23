package com.server.domain.push.mapper;

import org.mapstruct.Mapper;

import com.server.domain.push.dto.PushDto;
import com.server.domain.push.entity.Push;

@Mapper(componentModel = "spring")
public interface PushMapper {
    Push postDtoToPush(PushDto.Post postdto);
}
