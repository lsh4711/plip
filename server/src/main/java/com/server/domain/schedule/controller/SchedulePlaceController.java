package com.server.domain.schedule.controller;

import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.domain.record.entity.Record;
import com.server.domain.schedule.service.SchedulePlaceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule-places")
public class SchedulePlaceController {
    private final SchedulePlaceService schedulePlaceService;

    @GetMapping("/{schedulePlaceId}/records")
    public ResponseEntity<?> get(@PathVariable("schedulePlaceId") @Min(1) long schedulePlaceId) {
        List<Record> records = schedulePlaceService
                .findRecordsBySchedulePlaceId(schedulePlaceId);

        return ResponseEntity.ok(records);
    }
}
