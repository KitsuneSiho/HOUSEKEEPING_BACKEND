package com.housekeeping.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDTO {
    private Long commentId;
    private Long tipId;
    private Long userId;
    private String commentContent;
    private LocalDateTime commentCreatedDate;
    private String userNickname;  // 사용자 닉네임
    private String userProfileImageUrl;  // 사용자 프로필 이미지 URL
    private int userLevel;  // 사용자 레벨
}