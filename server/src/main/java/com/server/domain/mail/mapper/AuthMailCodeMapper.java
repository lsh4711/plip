package com.server.domain.mail.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.server.domain.mail.dto.AuthMailCodeDto;
import com.server.domain.mail.entity.AuthMailCode;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMailCodeMapper {
	AuthMailCode authMailCodeDtoPostToAuthMailCode(AuthMailCodeDto.Post request);
}
