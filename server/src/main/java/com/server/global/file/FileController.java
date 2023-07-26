package com.server.global.file;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files/images")
public class FileController {
    private final FileService fileService;

    // 알림 api에 제공할 알림용 이미지 url
    @GetMapping
    public ResponseEntity getImage(@RequestParam String region) {
        byte[] base64EncodedImage = fileService.getImageByRegion(region);

        return ResponseEntity.ok()
                // .contentType(MediaType.IMAGE_JPEG)
                .header("Content-Type", "image/webp")
                .body(base64EncodedImage);
    }

    // 테스트용
    @GetMapping("/test")
    public ResponseEntity getCustomImage(@RequestParam String name) {
        String extension = "png";
        byte[] base64EncodedImage = fileService.getImageByName(name, extension);

        return ResponseEntity.ok()
                // .contentType(MediaType.IMAGE_JPEG)
                .header("Content-Type", "image/" + extension)
                .body(base64EncodedImage);
    }

    // 이벤트용
    @GetMapping("/gifts")
    public ResponseEntity getImage(@RequestParam("id") long giftId) {
        byte[] image = fileService.getGiftImage(giftId);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                // .header("Content-Type", "image/jpeg")
                .body(image);
    }
}
