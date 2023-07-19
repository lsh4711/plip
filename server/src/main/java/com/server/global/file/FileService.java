package com.server.global.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

@Service
public class FileService {
    @Value("${spring.servlet.multipart.location}")
    private String basePath;

    public byte[] getImageByRegion(String region) {
        byte[] image = null;
        String fullPath = String.format("%s/%s.webp",
            basePath,
            region);
        File file = new File(fullPath);

        if (file.exists()) {
            try {
                image = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                throw new CustomException(ExceptionCode.IMAGE_NOT_FOUND);
            }
        }

        return image;
    }

    public byte[] getImageByName(String name, String extension) {
        byte[] image = null;
        String fullPath = String.format("%s/%s.%s",
            basePath,
            name,
            extension);
        File file = new File(fullPath);

        if (file.exists()) {
            try {
                image = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                throw new CustomException(ExceptionCode.IMAGE_NOT_FOUND);
            }
        }

        return image;
    }
}
