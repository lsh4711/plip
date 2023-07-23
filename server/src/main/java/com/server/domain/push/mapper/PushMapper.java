package com.server.domain.push.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.server.domain.member.repository.MemberRepository;
import com.server.domain.push.dto.PushDto;
import com.server.domain.push.entity.Push;
import com.server.global.utils.AuthUtil;

@Mapper(componentModel = "spring", imports = {AuthUtil.class})
public abstract class PushMapper {
    MemberRepository memberRepository;

    @Mapping(target = "member", expression = "java(memberRepository.findById(AuthUtil.getMemberId()).get())")
    public abstract Push postDtoToPush(PushDto.Post postdto);
}
