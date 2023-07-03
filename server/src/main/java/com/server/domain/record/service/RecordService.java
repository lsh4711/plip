package com.server.domain.record.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.server.domain.record.entity.Record;
import com.server.domain.record.repository.RecordRepository;
import com.server.global.exception.BusinessLogicException;
import com.server.global.exception.ExceptionCode;

@Service
public class RecordService {

    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public Record createRecord(Record record) {
        return recordRepository.save(record);
    }

    public Record findRecord(long recordId) {
        Optional<Record> optionalReecord = recordRepository.findById(recordId);
        Record findRecord = optionalReecord.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.RECORD_NOT_FOUND));

        return findRecord;
    }
}
