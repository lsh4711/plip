package com.server.domain.record.controller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
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
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.CustomUtil;
import com.server.global.utils.UriCreator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RecordController {
    private final static String RECORD_DEFAULT_URL = "/api/records";

    private final RecordMapper mapper;

    private final RecordService 
    recordService;

    private final ImageManager imageManager;

    //여행일지 등록
    @PostMapping("/{schedule-place-id}")
    public ResponseEntity<?> postRecord(@PathVariable("schedule-place-id") @Positive Long schedulePlaceId,
            @Valid @RequestBody RecordDto.Post requestBody) {
        Record record = mapper.recordPostToRecord(requestBody);

        Record createdRecord = recordService.createRecord(record, schedulePlaceId);

        URI location = UriCreator.createUri(RECORD_DEFAULT_URL, createdRecord.getRecordId());

        return ResponseEntity.created(location).build();
    }

    //여행일지 수정
    @PatchMapping("/{record-id}")
    public ResponseEntity<?> patchRecord(@PathVariable("record-id") @Positive long recordId,
            @Valid @RequestBody RecordDto.Patch requestBody) {
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
            new SingleResponseDto<>(mapper.recordToRecordResponse(record)), HttpStatus.OK);
    }

    //memberId로 여행일지 조회
    @GetMapping
    public ResponseEntity<?> getRecordsByMemberId(@RequestParam @Positive int page, @RequestParam @Positive int size) {
        Page<Record> pageRecords = recordService.findAllRecords(page - 1, size);
        List<Record> records = pageRecords.getContent();

        return new ResponseEntity<>(
            new MultiResponseDto<>(
                mapper.recordsToRecordResponses(records), pageRecords),
            HttpStatus.OK);
    }

    //여행일지 삭제
    @DeleteMapping("/{record-id}")
    public ResponseEntity deleteRecord(@PathVariable("record-id") @Positive long recordId) {
        recordService.deleteRecord(recordId);
        imageManager.deleteImgs(recordId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //이미지 업로드
    @PostMapping("/{record-id}/img")
    public ResponseEntity<?> uploadRecordImg(@PathVariable("record-id") long recordId,
            @RequestPart("images") List<MultipartFile> images) {
        long userId = CustomUtil.getAuthId();

        recordService.verfify(userId, recordId);

        try {
            Boolean uploadResult = imageManager.uploadImages(images, recordId);
            if (uploadResult) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading images: " + e.getMessage());
        }
    }

    // 이미지 1개 조회 (대표 이미지)
    @GetMapping("/{record-id}/img/{img-id}")
    public ResponseEntity<?> getRecordImg(@PathVariable("record-id") long recordId,
            @PathVariable("img-id") long imgId) {
        long userId = CustomUtil.getAuthId();

        recordService.verfify(userId, recordId);

        Resource imageFile = imageManager.loadImage(recordId, imgId);

        if (imageFile.exists()) {
            try {
                Resource imageResource = new UrlResource(imageFile.getURI());
                byte[] imageBytes = Files.readAllBytes(imageResource.getFile().toPath());
                String imageBase64 = Base64.getEncoder()
                        .encodeToString(Files.readAllBytes(imageResource.getFile().toPath()));

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);

                return new ResponseEntity<>(new SingleResponseDto<>(imageBase64), HttpStatus.OK);

            } catch (IOException e) {
                log.error("이미지 파일 읽기 오류: " + e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ExceptionCode.INTERNAL_SERVER_ERROR.getMessage());
            }
        } else {
            log.error("이미지 파일이 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ExceptionCode.IMAGE_NOT_FOUND.getMessage());
        }

    }

    //이미지 조회 - base64 인코딩된 걸 리턴
    @GetMapping("/{record-id}/img")
    public ResponseEntity<?> getRecordAllImg(@PathVariable("record-id") long recordId) {
        long userId = CustomUtil.getAuthId();

        recordService.verfify(userId, recordId);

        List<Resource> imageFiles = imageManager.loadImages(recordId);
        if (imageFiles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ExceptionCode.IMAGE_NOT_FOUND.getMessage());
        }

        List<String> imageBase64List = new ArrayList<>();
        for (Resource imageFile : imageFiles) {
            try {
                Resource imageResource = new UrlResource(imageFile.getURI());
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

    //이미지 삭제
    @DeleteMapping("/{record-id}/img/{img-id}")
    public ResponseEntity<?> deleteRecordImg(@PathVariable("record-id") long recordId,
            @PathVariable("img-id") long imgId) {

        imageManager.deleteImg(recordId, imgId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

}
