package com.server.domain.record;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageManager {

    public static Boolean uploadImages(List<MultipartFile> images, String dirName) throws Exception {
        short result = -1;

        try {
            File folder = new File(dirName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            for (MultipartFile image : images) {
                File destination = new File(dirName + File.separator + image.getOriginalFilename());
                image.transferTo(destination);
                result++;
            }
        } catch (Exception e) {
            log.error("사진 저장 에러 :" + e.getMessage());
            return Boolean.FALSE;
        }

        if (result == -1 || result < images.size() - 1) {
            return Boolean.FALSE;
        } else if (result == images.size() - 1) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public static List<Resource> loadImages(String dirName) {
        List<Resource> images = new ArrayList<>();

        try {
            File folder = new File(dirName);
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            Resource resource = new UrlResource(file.toURI());
                            images.add(resource);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("이미지 불러오기 에러: " + e.getMessage());
        }

        return images;
    }

    public static List<String> loadImageUrls(String dirName) {
        List<String> imageUrls = new ArrayList<>();

        try {
            File folder = new File(dirName);
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            String imageUrl = dirName + "/" + file.getName();
                            imageUrls.add(imageUrl);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("이미지 불러오기 에러: " + e.getMessage());
        }

        return imageUrls;
    }

}