package com.housekeeping.DTO;

import com.housekeeping.entity.enums.TipCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private TipCategory category;
    private LocalDateTime createdAt;
    private String authorName;
    private Long authorId;
}