package com.server.domain.place.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.domain.place.dto.PlaceDto;
import com.server.domain.place.entity.Place;
import com.server.domain.place.mapper.PlaceMapper;
import com.server.domain.place.repository.PlaceRepository;

@Service
public class PlaceService {
    private PlaceRepository placeRepository;
    private PlaceMapper placeMapper;

    public PlaceService(PlaceRepository placeRepository,
            PlaceMapper placeMapper) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
    }

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

    public List<Place> placeDtosToPlaces(List<PlaceDto.Post> placedtos) {
        return placeMapper.placeDtosToPlaces(placedtos);
    }
}
