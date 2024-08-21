package com.housekeeping.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ClothesClassficationController {

    @Value("${rembg.server.url}")
    private String flaskServerUrl;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // 이미지 파일을 Flask 서버로 전송
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new org.springframework.core.io.ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(flaskServerUrl + "/classify", requestEntity, String.class);

        // Flask 서버로부터 받은 분류 결과 반환
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
