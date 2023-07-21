package com.server.domain.record.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	List<String> store(List<MultipartFile> file, long recordId, long userId);

	String getImg(long recordId, long userId, long imgId);

	List<String> getImgs(long recordId, long userId);

	void deleteImg(long recordId, long userId, long imgId);

	void deleteImgs(long recordId, long userId);
}
