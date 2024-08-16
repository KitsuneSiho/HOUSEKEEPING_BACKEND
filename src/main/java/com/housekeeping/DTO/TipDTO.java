package com.housekeeping.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.housekeeping.entity.enums.TipCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipDTO {
    private Long tipId;
    private TipCategory tipCategory;
    private String tipTitle;
    private String tipContent;
    private int tipViews;
    private LocalDateTime tipCreatedDate;

    @JsonIgnoreProperties("tip")
    private List<CommentDTO> comments;
}