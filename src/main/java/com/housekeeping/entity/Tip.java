package com.housekeeping.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.housekeeping.entity.enums.TipCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "tip", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("tip")
    private List<Comment> comments = new ArrayList<>();
}