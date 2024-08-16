package com.housekeeping.controller;

import com.housekeeping.DTO.PostDTO;
import com.housekeeping.entity.Post;
import com.housekeeping.entity.User;
import com.housekeeping.entity.enums.TipCategory;
import com.housekeeping.service.PostService;
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
public class PostController {
    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto, @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .category(postDto.getCategory())
                .author(user)
                .createdAt(LocalDateTime.now())
                .build();

        Post savedPost = postService.createPost(post);
        return ResponseEntity.ok(convertToDTO(savedPost));
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable TipCategory category) {
        List<Post> posts = postService.getPostsByCategory(category);
        List<PostDTO> postDTOs = posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        log.info("Fetching post with id: {}", id);
        Post post = postService.getPostById(id);
        if (post == null) {
            log.warn("Post not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        PostDTO postDTO = convertToDTO(post);
        log.info("Returning post: {}", postDTO);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        Post updatedPost = postService.updatePost(id, postDTO.getTitle(), postDTO.getContent());
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    private PostDTO convertToDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .createdAt(post.getCreatedAt())
                .authorName(post.getAuthor() != null ? post.getAuthor().getNickname() : null)
                .build();
    }
}