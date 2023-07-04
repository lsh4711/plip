package com.server.domain.place.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.domain.place.entity.Place;
import com.server.domain.place.repository.PlaceRepository;

@Service
public class PlaceService {
    private PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> getPlaces(long memberId) {
        // placeRepository.fin

        return null;
    }

    public void savePlaces(List<Place> places) {
        // placeRepository.saveAll(places);

        // 디버깅용
        for (Place place : places) {
            Place savedPlace = placeRepository.save(place);
            System.out.println("CREATED");
        }
    }
}
