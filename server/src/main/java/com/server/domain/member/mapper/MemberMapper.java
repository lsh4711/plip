package com.server.domain.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.server.domain.member.dto.MemberDto;
import com.server.domain.member.entity.Member;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {
    Member memberDtoPostToMember(MemberDto.Post request);

    Member memberDtoPatchToMember(MemberDto.Patch request);
}
