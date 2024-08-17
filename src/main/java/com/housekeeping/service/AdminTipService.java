package com.housekeeping.service;

import com.housekeeping.entity.AdminTip;
import com.housekeeping.entity.enums.TipCategory;
import com.housekeeping.repository.AdminTipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminTipService {
    private final AdminTipRepository postRepository;

    @Transactional
    public AdminTip createPost(AdminTip post) {
        return postRepository.save(post);
    }

    @Transactional
    public void incrementViewCount(Long postId) {
        AdminTip post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }

    public List<AdminTip> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<AdminTip> getPostsByCategory(TipCategory category) {
        return postRepository.findByCategoryOrderByCreatedAtDesc(category);
    }

    public AdminTip getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Transactional
    public AdminTip updatePost(Long id, String title, String content) {
        AdminTip post = getPostById(id);
        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}