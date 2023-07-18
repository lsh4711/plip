package com.server.domain.schedule.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.domain.record.repository.RecordRepository;
import com.server.domain.schedule.entity.SchedulePlace;
import com.server.domain.schedule.repository.SchedulePlaceRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulePlaceService {
    private final SchedulePlaceRepository schedulePlaceRepository;
    private final RecordRepository recordRepository;

    public void saveSchedulePlaces(List<SchedulePlace> schedulePlaces) {
        schedulePlaceRepository.saveAll(schedulePlaces);

        // 디버깅용
        // for (SchedulePlace schedulePlace : schedulePlaces) {
        //     SchedulePlace savedSchedulePlace = schedulePlaceRepository.save(schedulePlace);
        //     System.out.println("CREATED");
        // }
    }

    // 추가
    public SchedulePlace findSchedulePlaceById(Long schedulePlaceId) {
        return schedulePlaceRepository.findById(schedulePlaceId)
                .orElseThrow(() -> new CustomException(
                    ExceptionCode.SCHEDULE_PLACE_NOT_FOUND));
    }

    public void deleteSchedulePlaces(List<SchedulePlace> schedulePlaces) {
        if (schedulePlaces != null) {
            schedulePlaceRepository.deleteAll(schedulePlaces);
        }

    }

    // public List<Record> findRecordsBySchedulePlaceId(long schedulePlaceId) {

    //     // List<Record> records = recordRepository
    //     //         .findAllBySchedulePlace_SchedulePlaceId(schedulePlaceId);
    //     return null;
    // }
}
