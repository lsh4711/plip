package com.server.domain.schedule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.schedule.service.SchedulePlaceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule-places")
public class SchedulePlaceController {

    private final SchedulePlaceService schedulePlaceService;

    // @GetMapping("/{schedulePlaceId}/records")
    // public ResponseEntity<?> get(@PathVariable("schedulePlaceId") @Positive long schedulePlaceId) {

    //     List<Record> records = schedulePlaceService.findRecordsBySchedulePlaceId(schedulePlaceId);
    //     List<Record> records = paginateRecords(allRecords, page, size);
    //     List<Response> recordResponses = recordMapper.recordsToRecordResponses(records);

    //     return new ResponseEntity<>(pagingResponse, HttpStatus.OK);
    // }
}
