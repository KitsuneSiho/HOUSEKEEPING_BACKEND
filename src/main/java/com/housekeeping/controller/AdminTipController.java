package com.housekeeping.controller;

import com.housekeeping.DTO.AdminTipDTO;
import com.housekeeping.entity.AdminTip;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.TipCategory;
import com.housekeeping.service.AdminTipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class AdminTipController {
    private final AdminTipService postService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminTipDTO> createPost(@RequestBody AdminTipDTO postDto, @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AdminTip post = AdminTip.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .category(postDto.getCategory())
                .author(user)
                .createdAt(LocalDateTime.now())
                .viewCount(0)
                .build();

        AdminTip savedPost = postService.createPost(post);
        return ResponseEntity.ok(convertToDTO(savedPost));
    }

    @GetMapping
    public ResponseEntity<List<AdminTipDTO>> getAllPosts() {
        List<AdminTipDTO> postDTOs = postService.getAllPosts().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDTOs);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<AdminTipDTO>> getPostsByCategory(@PathVariable TipCategory category) {
        List<AdminTipDTO> postDTOs = postService.getPostsByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminTipDTO> getPost(@PathVariable Long id) {
        log.info("Fetching post with id: {}", id);
        AdminTip post = postService.getPostById(id);
        if (post == null) {
            log.warn("Post not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        AdminTipDTO postDTO = convertToDTO(post);
        log.info("Returning post: {}", postDTO);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminTipDTO> updatePost(@PathVariable Long id, @RequestBody AdminTipDTO postDTO) {
        AdminTip updatedPost = postService.updatePost(id, postDTO.getTitle(), postDTO.getContent());
        return ResponseEntity.ok(convertToDTO(updatedPost));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<Void> incrementViewCount(@PathVariable Long id) {
        log.info("Increment view count for post with id: {}", id);
        postService.incrementViewCount(id);
        return ResponseEntity.ok().build();
    }

    private AdminTipDTO convertToDTO(AdminTip post) {
        return AdminTipDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .createdAt(post.getCreatedAt())
                .authorName(post.getAuthor() != null ? post.getAuthor().getNickname() : null)
                .authorId(post.getAuthor() != null ? post.getAuthor().getUserId() : null)
                .viewCount(post.getViewCount())
                .build();
    }
}