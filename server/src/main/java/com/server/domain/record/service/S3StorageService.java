package com.server.domain.record.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService{

    private static final String BUCKET_IMAGE_PATH = "record_images";
    @Value("${application.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;


    //이미지 저장
    @Override
    public List<String> store(List<MultipartFile> multipartFiles, long recordId, long userId){
        String dirName = BUCKET_IMAGE_PATH + "/" + userId + "/" + recordId;
        List<String> indexs = new ArrayList<>();

        //이미 저장된 이미지가 있을 경우, 가장 마지막에 저장된 index+1로 새로운 index
        int newIndex = getNewIndex(dirName);

        for(int i=0;i<multipartFiles.size();i++){
            File fileObj = convertMultiPartFileToFile(multipartFiles.get(i));

            //S3에 저장될 파일의 경로 및 이름
            int index=newIndex + i;
            String fileName = dirName.concat("/").concat(Integer.toString(index));
            indexs.add(Integer.toString(index));

            s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));

        }

        return indexs;
    }

    //이미지 1개 조회
    @Override
    public String getImg(long recordId, long userId, long imgId){
        String dirName = BUCKET_IMAGE_PATH + "/" + userId + "/" + recordId+"/"+imgId;
        URL url=null;
        if(s3Client.getObject(bucketName, dirName)!=null){
            url = s3Client.getUrl(bucketName, dirName);
        }
        return url.toString();
    }

    //이미지 여러 개 조회
    @Override
    public List<String> getImgs(long recordId, long userId){
        String dirName = BUCKET_IMAGE_PATH + "/" + userId + "/" + recordId;

        List<String> urls = new ArrayList<>();

        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request();
        listObjectsRequest.setBucketName(bucketName);
        listObjectsRequest.setPrefix(dirName);

        //prefix밑에 있는 객체들
        ListObjectsV2Result listObjectsResult;

        do {
            listObjectsResult = s3Client.listObjectsV2(listObjectsRequest);

            for (S3ObjectSummary objectSummary : listObjectsResult.getObjectSummaries()) {
                String key = objectSummary.getKey();
                URL url = s3Client.getUrl(bucketName, key);
                urls.add(url.toString());
            }

            listObjectsRequest.setContinuationToken(listObjectsResult.getNextContinuationToken());
        } while (listObjectsResult.isTruncated());

        return urls;
    }

    @Override
    public void deleteImg(long recordId, long userId, long imgId){
        String dirName = BUCKET_IMAGE_PATH + "/" + userId + "/" + recordId+"/"+imgId;

        s3Client.deleteObject(bucketName, dirName);
    }

    @Override
    public void deleteImgs(long recordId, long userId){
        String dirName = BUCKET_IMAGE_PATH + "/" + userId + "/" + recordId +"/";

        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request();
        listObjectsRequest.setBucketName(bucketName);
        listObjectsRequest.setPrefix(dirName);

        //prefix밑에 있는 객체들
        ListObjectsV2Result listObjectsResult;

        do {
            listObjectsResult = s3Client.listObjectsV2(listObjectsRequest);

            for (S3ObjectSummary objectSummary : listObjectsResult.getObjectSummaries()) {
                String[] parts = objectSummary.getKey().split("/");
                String imgId = parts[parts.length - 1];
                String newDirName=dirName +imgId;
                s3Client.deleteObject(bucketName,newDirName);
            }

            listObjectsRequest.setContinuationToken(listObjectsResult.getNextContinuationToken());
        } while (listObjectsResult.isTruncated());

    }


    private int getNewIndex(String dirName){
        ListObjectsV2Result result = s3Client.listObjectsV2(bucketName, dirName);
        List<S3ObjectSummary> objectSummaries = result.getObjectSummaries();

        int maxIndex=0;

        if(objectSummaries.size()==0){
            return maxIndex;
        }else{
            for(S3ObjectSummary objectSummary:objectSummaries){
                String key = objectSummary.getKey();
                String[] parts = key.split("/");
                if(parts.length>1){
                    int index = Integer.parseInt(parts[parts.length - 1]);
                    maxIndex = Math.max(maxIndex, index);
                }
            }
        }

        return maxIndex+1;
    }

    private File convertMultiPartFileToFile(MultipartFile multipartFile) {
        try{
            File convertedFile = new File(multipartFile.getOriginalFilename());
            FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
            return convertedFile;
        }catch (IOException e){
            throw new RuntimeException("Failed to convert MultipartFile to File", e);
        }
    }


}
