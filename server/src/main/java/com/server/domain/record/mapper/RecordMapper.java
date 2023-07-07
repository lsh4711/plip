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

    @Mapping(source = "member.memberId", target = "memberId")
    RecordDto.Response recordToRecordResponse(Record record);

    List<RecordDto.Response> recordsToRecordResponses(List<Record> records);

}
