package com.housekeeping.service;

import com.housekeeping.DTO.CommentDTO;
import com.housekeeping.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment saveComment(Long tipId, CommentDTO commentDTO);
    List<Comment> getCommentsByTipId(Long tipId);
    Comment updateComment(Long commentId, CommentDTO commentDTO);
    void deleteComment(Long commentId);
}