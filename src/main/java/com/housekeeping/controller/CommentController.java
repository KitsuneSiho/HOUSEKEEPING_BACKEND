package com.housekeeping.controller;

import com.housekeeping.DTO.CommentDTO;
import com.housekeeping.entity.Comment;
import com.housekeeping.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/tip/{tipId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long tipId, @RequestBody CommentDTO commentDTO) {
        Comment createdComment = commentService.saveComment(tipId, commentDTO);
        return new ResponseEntity<>(convertToDTO(createdComment), HttpStatus.CREATED);
    }

    @GetMapping("/tip/{tipId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByTipId(@PathVariable Long tipId) {
        List<Comment> comments = commentService.getCommentsByTipId(tipId);
        List<CommentDTO> commentDTOs = comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        Comment updatedComment = commentService.updateComment(commentId, commentDTO);
        return ResponseEntity.ok(convertToDTO(updatedComment));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    private CommentDTO convertToDTO(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .tipId(comment.getTip().getTipId())
                .userId(comment.getUser().getUserId())
                .commentContent(comment.getCommentContent())
                .commentCreatedDate(comment.getCommentCreatedDate())
                .build();
    }
}