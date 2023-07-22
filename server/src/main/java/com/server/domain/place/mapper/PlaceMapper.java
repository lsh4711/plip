package com.server.domain.place.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.server.domain.category.repository.CategoryRepository;
import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.entity.Place;
import com.server.domain.schedule.entity.Schedule;
import com.server.domain.schedule.entity.SchedulePlace;

@Mapper(componentModel = "spring")
public abstract class PlaceMapper {
    @Autowired
    CategoryRepository categoryRepository;

    @Mapping(target = "category", expression = "java(categoryRepository.findByCode(patchDto.getCategoryCode()))")
    public abstract Place patchDtoToPlace(PlaceDto.Patch patchDto);

    public abstract List<Place> patchDtosToPlaces(List<PlaceDto.Patch> patchDtos);

    public abstract List<List<Place>> patchDtoListsToPlaceLists(List<List<PlaceDto.Patch>> patchDtos);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(source = "place", target = "place")
    public abstract SchedulePlace placeToSchedulePlace(Place place, Schedule schedule);

    public List<SchedulePlace> placesToSchedulePlaces(List<Place> places, Schedule schedule) { // 나중에 수정
        List<SchedulePlace> schedulePlaces = new ArrayList<>();

        for (int i = 0; i < places.size(); i++) {
            Place place = places.get(i);
            SchedulePlace schedulePlace = placeToSchedulePlace(place, schedule);
            schedulePlaces.add(schedulePlace);
        }

        return schedulePlaces;
    }
}
