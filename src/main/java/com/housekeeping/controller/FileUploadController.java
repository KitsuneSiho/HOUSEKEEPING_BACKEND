package com.housekeeping.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${ncp.bucket.name}")
    private String bucketName;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            // 메타데이터 설정 (필요한 경우)
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // PutObjectRequest 생성 및 PublicRead 권한 설정
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            // 파일 업로드
            amazonS3.putObject(request);

            // 업로드된 파일의 URL 생성
            String fileUrl = amazonS3.getUrl(bucketName, fileName).toString();
            return fileUrl; // URL 반환
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file: " + fileName;
        }
    }

    @DeleteMapping("/delete")
    public String deleteFile(@RequestParam("fileName") String fileName) {
        try {
            // 파일 이름을 URL 인코딩
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());

            amazonS3.deleteObject(bucketName, encodedFileName);
            return "File deleted successfully: " + fileName;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Failed to encode file name: " + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to delete file: " + fileName;
        }
    }
}
