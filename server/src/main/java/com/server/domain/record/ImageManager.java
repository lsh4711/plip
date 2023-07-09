package com.server.domain.record;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.server.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageManager {

    @Value("${spring.servlet.multipart.location}")
    private String location;

    private final MemberService memberService;

    public  Boolean uploadImages(List<MultipartFile> images, String recordId) throws Exception {
        Long userId = getAuthenticatedMemberId();
        String dirName = location + "/" + userId + "/" + recordId;

        short result = -1;

        try {
            File folder = new File(dirName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            for (int i = 0; i < images.size(); i++) {
                MultipartFile image = images.get(i);
                String fileExtension = getFileExtension(image);
                String fileName = String.valueOf(i) + fileExtension;
                File destination = new File(dirName + File.separator + fileName);
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

    public  Resource loadImage(String recordId, String imgId){
        Long userId = getAuthenticatedMemberId();
        String dirName = location + "/" + userId + "/" + recordId;

        try{
            File folder = new File(dirName);
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for(File file:files){
                        String fileName = file.getName();
                        String fileId = fileName.substring(0, fileName.lastIndexOf('.'));
                        if(fileId.equals(imgId)){
                            return new UrlResource(file.toURI());
                        }
                    }
                }
            }
        }catch (Exception e) {
            log.error("이미지 불러오기 에러: " + e.getMessage());
        }
        return null;
    }


    public  List<Resource> loadImages(String recordId) {
        Long userId = getAuthenticatedMemberId();
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

    public void deleteImg(String recordId, String imgId) {
        Long userId = getAuthenticatedMemberId();
        String dirName = location + "/" + userId + "/" + recordId;

        try{
            File folder = new File(dirName);
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for(File file:files){
                        String fileName = file.getName();
                        String fileId = fileName.substring(0, fileName.lastIndexOf('.'));
                        if(fileId.equals(imgId)){
                            boolean isDeleted = file.delete();
                            if(isDeleted){
                                log.info("이미지 삭제 성공!");
                            }else{
                                log.error("이미지 삭제 실패!");
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            log.error("이미지 불러오기 에러: " + e.getMessage());
        }
    }

    //로그인한 사용자 아이디 리턴
    private  Long getAuthenticatedMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //현재 로그인한 사용자 이메일
        String username = (String) authentication.getPrincipal();

        // 로그인한 ID(이매일)로 Member를 찾아서 반환
        return memberService.findMemberByEmail(username).getMemberId();
    }


}