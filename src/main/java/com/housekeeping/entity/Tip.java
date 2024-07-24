package com.housekeeping.entity;

import com.housekeeping.entity.enums.TipCategory;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tipId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipCategory tipCategory;

    @Column(nullable = false)
    private String tipTitle;

    @Column(nullable = false)
    private String tipContent;

    @Column(nullable = false)
    private int tipViews = 0;

    @Column(nullable = false)
    private LocalDateTime tipCreatedDate = LocalDateTime.now();
}
