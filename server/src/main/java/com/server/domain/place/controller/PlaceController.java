package com.server.domain.place.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.place.service.PlaceService;
import com.server.domain.record.entity.Record;
import com.server.domain.record.mapper.RecordMapper;
import com.server.global.dto.MultiResponseDto;
import com.server.global.dto.MultiResponseDtoWithPage;

@RestController
@RequestMapping("/api/places")
public class PlaceController {
    private PlaceService placeService;

    private static RecordMapper recordMapper;

    private final static String PLACE_DEFAULT_URL = "/api/places";

    public PlaceController(PlaceService placeService, RecordMapper recordMapper) {
        this.placeService = placeService;
        this.recordMapper = recordMapper;
    }

    //placeId로 여행일지 찾기
    @GetMapping("/{place-id}/records")
    public ResponseEntity<?> getRecords(@PathVariable("place-id") @Positive Long placeId,
            @RequestParam @Positive int page, @RequestParam @Positive int size) {
        List<Record> allRecords = placeService.findRecords(placeId);
        List<Record> records = paginateRecords(allRecords, page, size);
        return new ResponseEntity<>(
            new MultiResponseDtoWithPage<>(recordMapper.recordsToRecordResponses(records), allRecords.size(), page,
                size),
            HttpStatus.OK);
    }

    private List<Record> paginateRecords(List<Record> allRecords, int page, int size) {
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, allRecords.size());
        return allRecords.subList(startIndex, endIndex);
    }
}
