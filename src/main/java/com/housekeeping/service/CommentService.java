package com.housekeeping.service;

import com.housekeeping.DTO.CommentDTO;
import com.housekeeping.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(CommentDTO commentDTO);
    List<Comment> getCommentsByTipId(Long tipId);
    Comment updateComment(Long id, CommentDTO commentDTO);
    void deleteComment(Long id);
}