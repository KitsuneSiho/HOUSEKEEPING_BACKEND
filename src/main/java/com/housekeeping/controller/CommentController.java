package com.housekeeping.controller;

import com.housekeeping.DTO.CommentDTO;
import com.housekeeping.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        CommentDTO createdComment = commentService.saveComment(tipId, commentDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/tip/{tipId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByTipId(@PathVariable Long tipId) {
        List<CommentDTO> commentDTOs = commentService.getCommentsByTipId(tipId);
        return ResponseEntity.ok(commentDTOs);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(commentId, commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}