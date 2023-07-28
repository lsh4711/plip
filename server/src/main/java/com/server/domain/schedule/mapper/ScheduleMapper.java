package com.server.domain.schedule.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.server.domain.member.repository.MemberRepository;
import com.server.domain.region.entity.Region;
import com.server.domain.region.repository.RegionRepository;
import com.server.domain.schedule.dto.ScheduleDto;
import com.server.domain.schedule.dto.ScheduleResponse;
import com.server.domain.schedule.entity.Schedule;
import com.server.global.utils.AuthUtil;
import com.server.global.utils.CustomRandom;

@Mapper(componentModel = "spring", imports = {AuthUtil.class, ChronoUnit.class})
public abstract class ScheduleMapper {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RegionRepository regionRepository;

    // @AfterMapping
    // 필드 따로 추가가능
    // defaultValue, defaultExpression
    // ignore로 제외 가능
    // 메소드 지정 가능
    // void로 이미 생성된 인스턴스에 맵핑 가능
    // 이터러블 맵핑으로 컬렉션의 타입 변환 가능
    @Mapping(target = "member", expression = "java(memberRepository.findById(AuthUtil.getMemberId()).get())")
    @Mapping(target = "memberCount", expression = "java(1)")
    @Mapping(target = "period", expression = "java((int)ChronoUnit.DAYS.between(postDto.getStartDate(), postDto.getEndDate()) + 1)")
    @Mapping(target = "region", expression = "java(regionRepository.findByEngName(postDto.getRegion()))")
    @Mapping(target = "title", expression = "java(createTitle(schedule.getRegion()))")
    public abstract Schedule postDtoToSchedule(ScheduleDto.Post postDto);

    // 확장시 쓰임
    // @Mapping(target = "memberCount", expression = "java(toMemberCount(patchDto.getMemberCount()))")
    // @Mapping(target = "period", expression = "java((int)ChronoUnit.DAYS.between(patchDto.getStartDate(), patchDto.getEndDate()) + 1)")
    // @Mapping(target = "region", expression = "java(regionRepository.findByEngName(patchDto.getRegion()))")
    // @Mapping(target = "title", expression = "java(toTitle(patchDto.getTitle(), schedule.getRegion()))")
    // // @Mapping(target = "tmpSchedulePlaces", expression = "java(toTmpSchedulePlaces())")
    // public abstract Schedule patchDtoToSchedule(ScheduleDto.Patch patchDto, long scheduleId);

    @Mapping(target = "memberCount", ignore = true)
    @Mapping(target = "region", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "title", expression = "java(toTitle(patchDto.getTitle()))")
    public abstract Schedule patchDtoToSchedule(ScheduleDto.Patch patchDto, long scheduleId);

    String createTitle(Region region) {
        String format = CustomRandom.getRandomTitleFormat();
        String korName = region.getKorName();

        return String.format(format, korName);
    }

    String toTitle(String title) {
        if (title != null && title.length() > 0) {
            return title;
        }

        return null;
    }

    int toMemberCount(int memberCount) {
        if (memberCount <= 0) {
            return 1;
        }

        return memberCount;
    }

    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "member.nickname", target = "nickname")
    @Mapping(source = "region.engName", target = "region")
    @Mapping(source = "region.korName", target = "korRegion")
    @Mapping(target = "startDate", expression = "java(toLocalDateTime(schedule.getStartDate()))")
    @Mapping(target = "endDate", expression = "java(toLocalDateTime(schedule.getEndDate()))")
    @Mapping(target = "placeSize", expression = "java(schedule.getSchedulePlaces().size())")
    public abstract ScheduleResponse scheduleToScheduleResponse(Schedule schedule);

    LocalDateTime toLocalDateTime(LocalDate localDate) {
        return localDate.atStartOfDay();
    }
}
