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

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        Comment createdComment = commentService.createComment(commentDTO);
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

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        Comment updatedComment = commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok(convertToDTO(updatedComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    private CommentDTO convertToDTO(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .tipId(comment.getTip().getTipId())
                .commentContent(comment.getCommentContent())
                .commentCreatedDate(comment.getCommentCreatedDate())
                .build();
    }
}