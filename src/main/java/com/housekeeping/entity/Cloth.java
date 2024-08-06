package com.housekeeping.entity;

import com.housekeeping.entity.enums.ClothSeason;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cloth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clothId; // 옷 아이디

    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩(Lazy Loading)을 통한 최적화
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String clothName; //옷 이름

    @Column(nullable = false)
    private String clothType; //옷 종류(상의, 하의, 신발, 악세서리) - 옷 상세히 나눠야함.

    @Column(nullable = false)
    private String clothColor; //옷 색깔

    @Column(nullable = false)
    private String clothMaterial; //옷 소재
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClothSeason clothSeason;

    private String clothCustomTag;

    @Column(nullable = false)
    private String imageUrl; // 이미지 URL
}
