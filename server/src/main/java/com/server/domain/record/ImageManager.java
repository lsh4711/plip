package com.server.domain.record;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.server.domain.member.service.MemberService;
import com.server.global.exception.CustomException;
import com.server.global.exception.ExceptionCode;
import com.server.global.utils.CustomUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageManager {

    @Value("${spring.servlet.multipart.location}")
    private String location;

    private final MemberService memberService;

    //이미지 업로드
    public List<String> uploadImages(List<MultipartFile> images, long recordId, long userId) throws Exception {

        String dirName = location + "/" + userId + "/" + recordId;

        short result = -1;

        List<String> indexs = new ArrayList<>();

        try {
            File folder = new File(dirName);
            if (!folder.exists() || folder.listFiles().length == 0) { //처음 사진을 저장하거나 저장된 사진이 없는 경우
                folder.mkdirs();

                for (int i = 0; i < images.size(); i++) {
                    MultipartFile image = images.get(i);
                    String fileExtension = getFileExtension(image);
                    String fileName = String.valueOf(i) + fileExtension;
                    File destination = new File(dirName + File.separator + fileName);
                    image.transferTo(destination);
                    result++;
                    indexs.add(String.valueOf(i));
                }
            } else { //이미 저장된 사진이 있는 경우
                File[] existingFiles = folder.listFiles();
                int lastIndex = -1;

                if (existingFiles.length > 0) {
                    // 기존 파일들을 검사하여 가장 높은 인덱스 찾기
                    for (File file : existingFiles) {
                        String fileName = file.getName();
                        int dotIndex = fileName.lastIndexOf(".");
                        if (dotIndex > 0) {
                            String indexStr = fileName.substring(0, dotIndex);
                            try {
                                int index = Integer.parseInt(indexStr);
                                if (index > lastIndex) {
                                    lastIndex = index;
                                }
                            } catch (NumberFormatException e) {
                                // 파일 이름이 숫자로 시작하지 않는 경우 무시
                            }
                        }
                    }
                    lastIndex++; // 다음 인덱스 계산
                } else {
                    lastIndex = 0; // 기존 파일이 없는 경우 0으로 시작
                }

                for (int i = 0; i < images.size(); i++) {
                    MultipartFile image = images.get(i);
                    String fileExtension = getFileExtension(image);
                    int index = lastIndex + i;
                    String fileName = index + fileExtension;
                    File destination = new File(dirName + File.separator + fileName);
                    image.transferTo(destination);
                    result++;
                    indexs.add(String.valueOf(index));
                }

                existingFiles = folder.listFiles();
            }

        } catch (Exception e) {
            log.error("사진 저장 에러 :" + e.getMessage());
            return null;
        }

        if (result == -1 || result < images.size() - 1) {
            return null;
        } else if (result == images.size() - 1) {
            return indexs;
        } else {
            return null;
        }
    }

    //이미지 조회 - 대표 이미지
    public Resource loadImage(long recordId, long userId, long imgId) {
        String dirName = location + "/" + userId + "/" + recordId;

        try {
            File folder = new File(dirName);
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        String fileName = file.getName().replaceAll("(?<=\\d)\\..*", "");
                        long fileId = Long.parseLong(fileName);
                        if (fileId == imgId) {
                            return new UrlResource(file.toURI());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("이미지 불러오기 에러: " + e.getMessage());
        }
        return null;
    }

    //전체 이미지 조회
    public List<Resource> loadImages(long recordId, long userId) {

        String dirName = location + "/" + userId + "/" + recordId;

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

    //이미지 파일 확장자 리턴하는 메서드
    private static String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String[] parts = originalFilename.split("\\.");
        if (parts.length > 1) {
            return "." + parts[parts.length - 1].toLowerCase();
        }
        return "";
    }

    //이미지 삭제
    public void deleteImg(long recordId, long userId, long imgId) {
        String dirName = location + '/' + userId + '/' + recordId;

        try {
            File folder = new File(dirName);
            File[] files = folder.listFiles();
            if (!folder.exists() || !folder.isDirectory() || files == null) {
                return;
            }
            for (File file : files) {
                String fileName = file.getName().replaceAll("(?<=\\d)\\..*", "");
                long fileId = Long.parseLong(fileName);
                if (fileId == imgId) {
                    boolean isDeleted = file.delete();
                    if (isDeleted) {
                        log.info("이미지 삭제 성공!");
                    } else {
                        log.error("이미지 삭제 실패!");
                    }
                }
            }

        } catch (Exception e) {
            log.error("이미지 불러오기 에러: " + e.getMessage());
            throw new CustomException(ExceptionCode.INTERNAL_SERVER_ERROR); // 이곳에 넣으면 의도대로 동작
        }
    }

    // 일지의 모든 이미지 삭제
    public void deleteImgs(long recordId) {
        Long userId = CustomUtil.getAuthId();
        String dirName = String.format("%s/%d/%d",
            location,
            userId,
            recordId);

        try {
            File folder = new File(dirName);
            if (!folder.exists() || !folder.isDirectory()) {
                return;
            }
            FileUtils.deleteDirectory(folder);
        } catch (Exception e) {
            log.error("삭제 실패: " + e.getMessage());
            throw new CustomException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

}
