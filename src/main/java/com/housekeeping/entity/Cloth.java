package com.housekeeping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cloth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clothId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String clothName;

    @Column(nullable = false)
    private String clothType;

    @Column(nullable = false)
    private String clothColor;

    @Column(nullable = false)
    private String clothMaterial;

    private String clothCustomTag;

    // Getters and Setters
}
