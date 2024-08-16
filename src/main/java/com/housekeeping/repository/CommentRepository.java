package com.housekeeping.repository;

import com.housekeeping.entity.Comment;
import com.housekeeping.repository.custom.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findByTipTipId(Long tipId);
}