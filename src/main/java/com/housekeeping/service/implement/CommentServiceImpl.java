package com.housekeeping.service.implement;

import com.housekeeping.DTO.CommentDTO;
import com.housekeeping.entity.Comment;
import com.housekeeping.entity.Tip;
import com.housekeeping.repository.CommentRepository;
import com.housekeeping.repository.TipRepository;
import com.housekeeping.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TipRepository tipRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, TipRepository tipRepository) {
        this.commentRepository = commentRepository;
        this.tipRepository = tipRepository;
    }

    @Override
    public Comment createComment(CommentDTO commentDTO) {
        Tip tip = tipRepository.findById(commentDTO.getTipId())
                .orElseThrow(() -> new RuntimeException("Tip not found with id: " + commentDTO.getTipId()));

        Comment comment = Comment.builder()
                .tip(tip)
                .commentContent(commentDTO.getCommentContent())
                .build();

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByTipId(Long tipId) {
        return commentRepository.findByTipTipId(tipId);
    }

    @Override
    public Comment updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));

        comment.setCommentContent(commentDTO.getCommentContent());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}