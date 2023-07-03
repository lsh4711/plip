package com.server.domain.test.mapper;

import org.mapstruct.Mapper;

import com.server.domain.test.dto.TestDto;
import com.server.domain.test.entity.Test;

@Mapper(componentModel = "spring")
public interface TestMapper {
    Test postDtoToTest(TestDto.Post postDto);

    Test patchDtoToTest(TestDto.Patch patchDto);
}
