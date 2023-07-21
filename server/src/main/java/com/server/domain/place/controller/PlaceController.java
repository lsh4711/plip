package com.server.domain.place.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.server.domain.place.service.PlaceService;
import com.server.domain.record.entity.Record;
import com.server.domain.record.mapper.RecordMapper;

import lombok.RequiredArgsConstructor;

// @RestController
@RequiredArgsConstructor
@RequestMapping("/api/places")
public class PlaceController {
	private final PlaceService placeService;

	private final RecordMapper recordMapper;

	// 해당 장소를 방문한 모든 회원의 일지를 조회, 정렬 필요한지 확인해야함
	// + 소셜 확장 전까지 접근 제한
	// @GetMapping("/{place-id}/records")
	// public ResponseEntity<?> getRecords(@PathVariable("place-id") @Positive Long placeId,
	//         @RequestParam @Positive int page, @RequestParam @Positive int size) {
	//     List<Record> allRecords = placeService.findRecords(placeId);
	//     List<Record> records = paginateRecords(allRecords, page, size);
	//     List<Response> recordResponses = recordMapper.recordsToRecordResponses(records);
	//     MultiResponseDtoWithPage pagingResponse = new MultiResponseDtoWithPage<>(
	//         recordResponses,
	//         allRecords.size(),
	//         page,
	//         size);

	//     return new ResponseEntity<>(pagingResponse, HttpStatus.OK);
	// }

	private List<Record> paginateRecords(List<Record> allRecords, int page, int size) {
		int startIndex = (page - 1) * size;
		int endIndex = Math.min(startIndex + size, allRecords.size());

		return allRecords.subList(startIndex, endIndex);
	}
}
