package com.housekeeping.DTO;

import com.housekeeping.entity.Comment;
import com.housekeeping.entity.enums.TipCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder //빌더 패턴을 자동으로 구현
@NoArgsConstructor //매개변수가 없는 기본 생성자를 자동으로 생성
@AllArgsConstructor //클래스의 모든 필드를 매개변수로 받는 생성
public class TipDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tipId;
    private TipCategory tipCategory;
    private String tipTitle;
    private String tipContent;
    private int tipViews;
    private LocalDateTime tipCreatedDate;
    private List<Comment> comments;
}
