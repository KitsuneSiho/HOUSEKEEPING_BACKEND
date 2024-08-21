package com.housekeeping.service;

import com.housekeeping.DTO.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO saveComment(Long tipId, CommentDTO commentDTO);
    List<CommentDTO> getCommentsByTipId(Long tipId);
    CommentDTO updateComment(Long commentId, CommentDTO commentDTO);
    void deleteComment(Long commentId);
}