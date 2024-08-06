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
import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${ncp.bucket.name}")
    private String bucketName;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString(); // 랜덤 UUID 생성
        String fileExtension = "";

        // 파일 확장자 추출
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // 새 파일명 생성
        String newFileName = fileName + fileExtension;

        try {
            // 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // PutObjectRequest 생성 및 PublicRead 권한 설정
            PutObjectRequest request = new PutObjectRequest(bucketName, newFileName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            // 파일 업로드
            amazonS3.putObject(request);

            // 업로드된 파일의 URL 생성
            String fileUrl = amazonS3.getUrl(bucketName, newFileName).toString();
            return fileUrl; // URL 반환
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file: " + originalFileName;
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String fileName) {
        try {
            // 파일 이름을 URL 인코딩
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            encodedFileName = encodedFileName.replaceAll("\\+", "%20"); // 공백 처리

            amazonS3.deleteObject(bucketName, encodedFileName);
            return ResponseEntity.ok("파일이 성공적으로 삭제되었습니다: " + fileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 이름 인코딩 실패: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 실패: " + fileName);
        }
    }

}
