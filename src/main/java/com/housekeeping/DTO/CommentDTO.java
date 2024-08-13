package com.housekeeping.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long commentId;
    private Long tipId;  // Tip 엔티티 대신 tipId만 포함
    private Long userId; // User 엔티티 대신 userId만 포함
//    private String userName; // 작성자
    private String commentContent;
    private LocalDateTime commentCreatedDate;

}