package com.server.domain.place.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.place.service.PlaceService;

@RestController
@RequestMapping("/api/places")
public class PlaceController {
    private PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/{placeId}/records")
    public ResponseEntity getRecordsByPlaceId(Authentication authentication,
            @PathVariable("placeId") long placeId) {
        // 멤버 id 조회해야함
        long memberId = 1L;
        Record record;

        return null;
    }
}
