package com.server.domain.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.server.domain.member.dto.MemberDto;
import com.server.domain.member.entity.Member;
import com.server.global.auth.utils.OAuthAttributes;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {
    Member memberDtoPostToMember(MemberDto.Post request);

    Member memberDtoPatchToMember(MemberDto.Patch request);

    MemberDto.Response memberToMemberDtoResponse(Member member);

    default Member oauthAttributesToMember(OAuthAttributes oAuthAttributes) {
        if (oAuthAttributes == null) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.email(oAuthAttributes.getEmail());
        member.nickname(oAuthAttributes.getNickname());
        member.role(Member.Role.SOCIAL);

        return member.build();
    }
}
