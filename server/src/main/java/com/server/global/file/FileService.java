package com.server.global.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;

import io.jsonwebtoken.io.Encoders;

@Service
public class FileService {
    @Value("${spring.servlet.multipart.location}")
    private String basePath;

    @Value("${file.path}/gifts")
    private String giftDir;

    public byte[] getImage(String path) {
        File file = new File(path);
        byte[] image = null;

        if (file.exists()) {
            try {
                image = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                throw new CustomException(ExceptionCode.IMAGE_NOT_FOUND);
            }
        }

        return image;
    }

    public String getBase64EncodedImage(String path) {
        byte[] image = getImage(path);
        String encodedImage = Encoders.BASE64.encode(image);

        return encodedImage;
    }

    public byte[] getImageByRegion(String region) {
        String fullPath = String.format("%s/%s.webp",
            basePath,
            region);
        byte[] image = getImage(fullPath);

        return image;
    }

    // 테스트용
    public byte[] getImageByName(String name, String extension) {
        String fullPath = String.format("%s/%s.%s",
            basePath,
            name,
            extension);
        byte[] image = getImage(fullPath);

        return image;
    }

    // 이벤트용
    public byte[] getGiftImage(long giftId) {
        String path = String.format("%s/%d.jpg", giftDir, giftId);

        byte[] image = getImage(path);

        return image;
    }
}
