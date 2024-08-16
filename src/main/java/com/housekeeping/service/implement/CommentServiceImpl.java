package com.housekeeping.service.implement;

import com.housekeeping.DTO.CommentDTO;
import com.housekeeping.entity.Comment;
import com.housekeeping.entity.Tip;
import com.housekeeping.entity.User;
import com.housekeeping.repository.CommentRepository;
import com.housekeeping.repository.TipRepository;
import com.housekeeping.repository.UserRepository;
import com.housekeeping.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TipRepository tipRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, TipRepository tipRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.tipRepository = tipRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment saveComment(Long tipId, CommentDTO commentDTO) {
        Tip tip = tipRepository.findById(tipId)
                .orElseThrow(() -> new RuntimeException("Tip not found with id: " + tipId));
        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + commentDTO.getUserId()));

        Comment comment = Comment.builder()
                .tip(tip)
                .user(user)
                .commentContent(commentDTO.getCommentContent())
                .commentCreatedDate(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByTipId(Long tipId) {
        return commentRepository.findByTipTipId(tipId);
    }

    @Override
    public Comment updateComment(Long commentId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        comment.setCommentContent(commentDTO.getCommentContent());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        commentRepository.delete(comment);
    }
}