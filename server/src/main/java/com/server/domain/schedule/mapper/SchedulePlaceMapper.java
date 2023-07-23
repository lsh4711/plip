package com.server.domain.schedule.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.server.domain.place.dto.PlaceResponse;
import com.server.domain.place.entity.Place;
import com.server.domain.record.dto.RecordDto;
import com.server.domain.record.entity.Record;
import com.server.domain.record.mapper.RecordMapper;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;

@Mapper(componentModel = "spring")
public abstract class SchedulePlaceMapper {
    @Autowired
    RecordMapper recordMapper;

    @Mapping(source = "schedulePlace.schedule.scheduleId", target = "scheduleId")
    @Mapping(source = "place.category.code", target = "category")
    @Mapping(source = "place.category.name", target = "categoryName")
    @Mapping(source = "place.days", target = "days")
    @Mapping(source = "place.orders", target = "orders")
    @Mapping(source = "schedulePlace.schedulePlaceId", target = "schedulePlaceId")
    public abstract PlaceResponse schedulePlaceToPlaceResponse(SchedulePlace schedulePlace, Place place);

    public List<List<PlaceResponse>> schedulePlacesToPlaceResponseLists(List<SchedulePlace> schedulePlaces,
            Schedule schedule) {
        int period = schedule.getPeriod();
        List<List<PlaceResponse>> placeResponseLists = new ArrayList<>();

        // 최적화 가능
        for (SchedulePlace schedulePlace : schedulePlaces) {
            int days = schedulePlace.getDays();
            Place place = schedulePlace.getPlace();
            PlaceResponse placeResponse = schedulePlaceToPlaceResponse(schedulePlace, place);
            while (placeResponseLists.size() < days) {
                placeResponseLists.add(new ArrayList<>());
            }
            placeResponseLists.get(days - 1).add(placeResponse);
        }
        while (placeResponseLists.size() < period) {
            placeResponseLists.add(new ArrayList<>());
        }

        return placeResponseLists;
    }

    public Map<Long, List<RecordDto.Response>> toRecordResponseMap(
            List<SchedulePlace> schedulePlaces) {
        Map<Long, List<RecordDto.Response>> map = new HashMap<>();

        for (SchedulePlace schedulePlace : schedulePlaces) {
            long schedulePlaceId = schedulePlace.getSchedulePlaceId();
            List<Record> records = schedulePlace.getRecords();
            List<RecordDto.Response> recordResponses = recordMapper
                    .recordsToRecordResponses(records);
            map.put(schedulePlaceId, recordResponses);
        }

        return map;
    }
}
