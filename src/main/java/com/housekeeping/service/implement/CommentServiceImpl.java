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
import java.util.stream.Collectors;

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
    public CommentDTO saveComment(Long tipId, CommentDTO commentDTO) {
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

        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    @Override
    public List<CommentDTO> getCommentsByTipId(Long tipId) {
        List<Comment> comments = commentRepository.findByTipTipId(tipId);
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        comment.setCommentContent(commentDTO.getCommentContent());
        Comment updatedComment = commentRepository.save(comment);
        return convertToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        commentRepository.delete(comment);
    }

    private CommentDTO convertToDTO(Comment comment) {
        User user = comment.getUser();
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .tipId(comment.getTip().getTipId())
                .userId(user.getUserId())
                .commentContent(comment.getCommentContent())
                .commentCreatedDate(comment.getCommentCreatedDate())
                .userNickname(user.getNickname())
                .userProfileImageUrl(user.getProfileImageUrl())
                .userLevel(user.getLevel().getLevelLevel())  // levelLevel 사용
                .build();
    }
}