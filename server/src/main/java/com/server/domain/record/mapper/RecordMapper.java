package com.server.domain.record.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.server.domain.record.dto.RecordDto;
import com.server.domain.record.entity.Record;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecordMapper {
    Record recordPostToRecord(RecordDto.Post requestBody);

    Record recordPatchToRecord(RecordDto.Patch requestBody);

    @Mapping(target = "memberId", source = "record.member.memberId")
    @Mapping(source = "schedulePlace.place.name", target = "placeName") // 클라이언트 요청
    @Mapping(source = "schedulePlace.days", target = "days") // 클라이언트 요청
    RecordDto.Response recordToRecordResponse(Record record);

    List<RecordDto.Response> recordsToRecordResponses(List<Record> records);
}
