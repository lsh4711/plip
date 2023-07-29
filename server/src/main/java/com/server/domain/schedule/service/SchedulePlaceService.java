package com.server.domain.schedule.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.server.domain.schedule.entity.SchedulePlace;
import com.server.domain.schedule.repository.SchedulePlaceRepository;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.CustomBeanUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulePlaceService {
    private final SchedulePlaceRepository schedulePlaceRepository;

    public void saveSchedulePlace(SchedulePlace schedulePlace) {
        schedulePlaceRepository.save(schedulePlace);
    }

    public List<SchedulePlace> saveSchedulePlaces(List<SchedulePlace> schedulePlaces) {
        return schedulePlaceRepository.saveAll(schedulePlaces);
    }

    public List<SchedulePlace> updateSchedulePlaces(List<SchedulePlace> updateSchedulePlaces,
            List<SchedulePlace> schedulePlaces) {
        if (updateSchedulePlaces.size() == 0) {
            return schedulePlaces;
        }

        List<SchedulePlace> updatedSchedulePlaces = new ArrayList<>();
        Map<Long, SchedulePlace> map = schedulePlaces.stream()
                .collect(Collectors.toMap(SchedulePlace::getSchedulePlaceId,
                    schedulePlace -> schedulePlace));

        for (SchedulePlace schedulePlace : updateSchedulePlaces) {
            Long schedulePlaceId = schedulePlace.getSchedulePlaceId();
            if (schedulePlaceId == null) {
                schedulePlaceRepository.save(schedulePlace);
                updatedSchedulePlaces.add(schedulePlace);
                continue;
            }
            SchedulePlace foundSchedulePlace = schedulePlaceRepository
                    .findById(schedulePlaceId).get();
            CustomBeanUtils.copyNonNullProperties(schedulePlace, foundSchedulePlace);
            updatedSchedulePlaces.add(foundSchedulePlace);
            schedulePlaceRepository.save(foundSchedulePlace);
            map.remove(schedulePlaceId);
        }
        for (Entry<Long, SchedulePlace> entry : map.entrySet()) {
            SchedulePlace schedulePlace = entry.getValue();
            schedulePlace.setSchedule(null);
            schedulePlaceRepository.save(schedulePlace);
        }

        return updatedSchedulePlaces;
    }

    // 추가
    public SchedulePlace findSchedulePlaceById(Long schedulePlaceId) {
        return schedulePlaceRepository.findById(schedulePlaceId)
                .orElseThrow(() -> new CustomException(
                    ExceptionCode.SCHEDULE_PLACE_NOT_FOUND));
    }

    // 기존: 변경 장소에 없는 장소만 삭제
    // 현재: 모두 삭제
    public void deleteSchedulePlaces(Collection<SchedulePlace> schedulePlaces) {
        schedulePlaceRepository.deleteAll(schedulePlaces);
    }

}
