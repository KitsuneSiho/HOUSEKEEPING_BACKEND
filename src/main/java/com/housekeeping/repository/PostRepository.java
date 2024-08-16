package com.housekeeping.repository;

import com.housekeeping.entity.Post;
import com.housekeeping.entity.enums.TipCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findByCategoryOrderByCreatedAtDesc(TipCategory category);
}