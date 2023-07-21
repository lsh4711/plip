package com.server.global.file;

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

	// 카카오 api에 제공할 일정 알림 메시지의 기본 배경사진 url
	@GetMapping
	public ResponseEntity getImage(@RequestParam String region) {
		byte[] base64EncodedImage = fileService.getImageByRegion(region);

		return ResponseEntity.ok()
			// .contentType(MediaType.IMAGE_JPEG)
			.header("Content-Type", "image/webp")
			.body(base64EncodedImage);
	}

	@GetMapping("/test")
	public ResponseEntity getCustomImage(@RequestParam String name) {
		String extension = "png";
		byte[] base64EncodedImage = fileService.getImageByName(name, extension);

		return ResponseEntity.ok()
			// .contentType(MediaType.IMAGE_JPEG)
			.header("Content-Type", "image/" + extension)
			.body(base64EncodedImage);
	}
}
