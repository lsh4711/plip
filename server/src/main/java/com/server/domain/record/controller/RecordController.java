package com.server.domain.record.controller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.server.domain.record.ImageManager;
import com.server.domain.record.dto.ImageResponseDto;
import com.server.domain.record.dto.RecordDto;
import com.server.domain.record.entity.Record;
import com.server.domain.record.mapper.RecordMapper;
import com.server.domain.record.service.RecordService;
import com.server.global.dto.MultiResponseDto;
import com.server.global.dto.SingleResponseDto;
import com.server.global.utils.UriCreator;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/records")
@Validated
@Slf4j
public class RecordController {
    private final static String RECORD_DEFAULT_URL = "/api/records";
    private final RecordMapper mapper;

    private final RecordService recordService;

    @Value("${spring.servlet.multipart.location}")
    private String location;

    public RecordController(RecordMapper mapper, RecordService recordService) {
        this.mapper = mapper;
        this.recordService = recordService;
    }

    //여행일지 등록
    @PostMapping
    public ResponseEntity<?> postRecord(@Valid @RequestBody RecordDto.Post requestBody) {
        Record record = mapper.recordPostToRecord(requestBody);

        Record createdRecord = recordService.createRecord(record);

        URI location = UriCreator.createUri(RECORD_DEFAULT_URL, createdRecord.getRecordId());

        return ResponseEntity.created(location).build();
    }

    //여행일지 수정
    @PatchMapping("/{record-id}")
    public ResponseEntity<?> patchRecord(@PathVariable("record-id") @Positive long recordId,
        @Valid @RequestBody RecordDto.Patch requestBody){
        requestBody.setRecordId(recordId);

        Record record = mapper.recordPatchToRecord(requestBody);

        Record updatedRecord = recordService.updateRecord(record);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.recordToRecordResponse(updatedRecord)),
            HttpStatus.OK);
    }

    //recordId로 여행일지 조회
    @GetMapping("/{record-id}")
    public ResponseEntity<?> getRecord(@PathVariable("record-id") @Positive long recordId) {
        Record record = recordService.findRecord(recordId);

        return new ResponseEntity<>(
            new SingleResponseDto<>(mapper.recordToRecordResponse(record)), HttpStatus.OK
        );
    }

    //memberId로 여행일지 조회
    @GetMapping
    public ResponseEntity<?> getRecordsByMemberId(@RequestParam @Positive int page, @RequestParam @Positive int size){
        Page<Record> pageRecords = recordService.findAllRecords(page-1, size);
        List<Record> records = pageRecords.getContent();

        return new ResponseEntity<>(
            new MultiResponseDto<>(
                mapper.recordsToRecordResponses(records), pageRecords
            ),
            HttpStatus.OK
        );
    }

    //placeId로 여행일지 조회
    // @GetMapping("/place/{place-id}")
    // public ResponseEntity<?> getRecordsByPlaceId(@PathVariable Long placeId){
    //     List<Record> records = new ArrayList<>();
    //
    //     Optional<Schedule_Place> schedulePlaceOptional = schedulePlaceRepository.findById(placeId);
    //     if (schedulePlaceOptional.isPresent()) {
    //         Schedule_Place schedulePlace = schedulePlaceOptional.get();
    //         records = schedulePlace.getRecords();
    //     }
    //
    //     return records;
    // }

    //여행일지 삭제
    @DeleteMapping("/{record-id}")
    public ResponseEntity deleteRecord(@PathVariable("record-id") @Positive long recordId){
        recordService.deleteRecord(recordId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    //이미지 업로드
    @PostMapping("/{record-id}/img")
    public ResponseEntity<?> uploadRecordImg(@PathVariable("record-id") String recordId,
        @RequestParam("images") List<MultipartFile> images) {
        Long userId = 1L;
        String dirName = location + "/" + userId + "/" + recordId;

        try {
            Boolean uploadResult = ImageManager.uploadImages(images, dirName);
            if (uploadResult) {
                URI location = UriCreator.createUri(RECORD_DEFAULT_URL, Long.parseLong(recordId));
                return ResponseEntity.created(location).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error occurred while uploading images: " + e.getMessage());
        }
    }


    //이미지 조회 - 바이트 코드를 리턴
    @GetMapping("/{record-id}/img")
    public ResponseEntity<?> getRecordImg(@PathVariable("record-id") String recordId) {
        Long userId = 1L;
        String dirName = location + "/" + userId + "/" + recordId;

        List<Resource> imageFiles = ImageManager.loadImages(dirName);
        if (imageFiles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<String> imageBase64List = new ArrayList<>();
        for (Resource imageFile : imageFiles) {
            try {
                Resource imageResource = new UrlResource(imageFile.getURI());
                System.out.println(imageFile.getURI());
                if (imageResource.exists()) {
                    String imageBase64 = Base64.getEncoder()
                        .encodeToString(Files.readAllBytes(imageResource.getFile().toPath()));
                    imageBase64List.add(imageBase64);
                }
            } catch (IOException e) {
                log.error("이미지 파일 읽기 오류 : " + e.getMessage(), e);
            }
        }

        if (imageBase64List.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ImageResponseDto imageResponseDto = new ImageResponseDto();
        imageResponseDto.setImages(imageBase64List);

        return ResponseEntity.ok(imageResponseDto);
    }


}
