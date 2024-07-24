package com.housekeeping.entity;

import com.housekeeping.entity.enums.FoodCategory;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String foodName;

    @Enumerated(EnumType.STRING)
    private FoodCategory foodCategory;

    @Column(nullable = false)
    private int foodQuantity = 1;

    private String foodMemo;

    private LocalDateTime foodExpirationDate;
}
