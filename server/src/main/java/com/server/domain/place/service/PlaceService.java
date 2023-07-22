package com.server.domain.place.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.domain.place.entity.Place;
import com.server.domain.place.repository.PlaceRepository;
import com.server.global.utils.CustomBeanUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public List<Place> savePlaces(List<Place> places) {
        return placeRepository.saveAll(places);
    }

    // apiId를 기준으로 중복 방지
    public List<Place> savePlaceLists(List<List<Place>> placeLists) {
        if (placeLists == null) {
            return null;
        }

        List<Place> newPlaces = new ArrayList<>();

        for (int i = 0; i < placeLists.size(); i++) {
            List<Place> places = placeLists.get(i);
            for (int j = 0; j < places.size(); j++) {
                Place place = places.get(j);
                long apiId = place.getApiId();
                Place foundPlace = placeRepository.findByApiId(apiId);
                if (foundPlace != null) {
                    place = CustomBeanUtils.copyNonNullProperties(place, foundPlace);
                }
                Place savedPlace = placeRepository.save(place);
                savedPlace.setDays(i + 1);
                savedPlace.setOrders(j + 1);
                newPlaces.add(savedPlace);
            }
        }

        return newPlaces;
    }

    // 소셜 확장 시 사용됨, 현재 PlaceController에서 숨김
    // public List<Record> findRecords(Long placeId) {
    //     List<Record> foundRecords = new ArrayList<>();
    //     List<SchedulePlace> schedulePlaceList = schedulePlaceRepository
    //             .findByPlacePlaceId(placeId);

    //     if (schedulePlaceList.size() == 0) {
    //         throw new CustomException(ExceptionCode.NO_VISITOR);
    //     }
    //     for (SchedulePlace schedulePlace : schedulePlaceList) {
    //         List<Record> records = schedulePlace.getRecords();
    //         foundRecords.addAll(records);
    //     }

    //     return foundRecords;
    // }
}
