package com.server.domain.place.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.entity.Place;
import com.server.domain.place.mapper.PlaceMapper;
import com.server.domain.place.repository.PlaceRepository;
import com.server.domain.record.entity.Record;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;
import com.server.domain.schedule.repository.SchedulePlaceRepository;
import com.server.domain.schedule.service.SchedulePlaceService;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    // final이 빠지면 RequiredArgsConstructor로 생성이 안됨!
    private final SchedulePlaceService schedulePlaceService;
    private final SchedulePlaceRepository schedulePlaceRepository;

    public List<Place> getPlaces(long memberId) {
        // placeRepository.fin

        return null;
    }

    public List<Place> savePlaces(List<Place> places) {
        return placeRepository.saveAll(places);

        // 디버깅용
        // for (Place place : places) {
        //     Place savedPlace = placeRepository.save(place);
        //     System.out.println("CREATED");
        // }
    }

    public void savePlaceLists(Schedule savedSchedule,
            List<List<Place>> placeLists) {
        for (List<Place> places : placeLists) {
            if (places.size() == 0) {
                throw new CustomException(ExceptionCode.EMPTY_PLACES);
            }
            // 나중에 Map API 데이터에서 유니크 값들을 확인하고 중복 저장 방지 필요
            placeRepository.saveAll(places);
        }

        List<SchedulePlace> schedulePlaces = new ArrayList<>();
        for (int i = 0; i < placeLists.size(); i++) {
            List<Place> savedPlaces = placeLists.get(i);
            for (int j = 0; j < savedPlaces.size(); j++) {
                Place savedPlace = savedPlaces.get(j);
                SchedulePlace schedulePlace = new SchedulePlace();
                schedulePlace.setSchedule(savedSchedule);
                schedulePlace.setPlace(savedPlace);
                schedulePlace.setDays(i + 1);
                schedulePlace.setOrders(j + 1);
                schedulePlaces.add(schedulePlace);
            }
        }

        schedulePlaceService.saveSchedulePlaces(schedulePlaces);

        // return placeLists;
    }

    public List<Place> placeDtosToPlaces(List<PlaceDto.Post> placedtos) {
        return placeMapper.postDtosToPlaces(placedtos);
    }

    public List<Record> findRecords(Long placeId) {
        List<Record> records = new ArrayList<>();

        List<SchedulePlace> schedulePlaceList = schedulePlaceRepository.findByPlacePlaceId(placeId);

        if (schedulePlaceList.size() > 0) {
            for (SchedulePlace schedulePlace : schedulePlaceList) {
                for (Record record : schedulePlace.getRecords()) {
                    records.add(record);
                }
            }
        }
        return records;
    }
}
