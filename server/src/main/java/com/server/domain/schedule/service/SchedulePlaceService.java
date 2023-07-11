package com.server.domain.schedule.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.domain.schedule.entity.SchedulePlace;
import com.server.domain.schedule.repository.SchedulePlaceRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

@Service
public class SchedulePlaceService {
    private SchedulePlaceRepository schedulePlaceRepository;

    public SchedulePlaceService(SchedulePlaceRepository schedulePlaceRepository) {
        this.schedulePlaceRepository = schedulePlaceRepository;
    }

    public void saveSchedulePlaces(List<SchedulePlace> schedulePlaces) {
        //placeRepository.saveAll(schedulePlaces);

        //디버깅용
        for (SchedulePlace schedulePlace : schedulePlaces) {
            SchedulePlace savedSchedulePlace = schedulePlaceRepository.save(schedulePlace);
            System.out.println("CREATED");
        }
    }

    //추가
    public SchedulePlace findSchedulePlaceById(Long schedulePlaceId){
        return schedulePlaceRepository.findById(schedulePlaceId).orElseThrow(()-> new CustomException(
            ExceptionCode.SCHEDULE_PLACE_NOT_FOUND));

    }
}
