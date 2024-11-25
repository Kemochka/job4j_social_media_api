package ru.job4j.socialmedia.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.service.post.PostService;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private final PostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> findPostsByUserId(@PathVariable()
                                                        @NotNull
                                                        @Min(value = 1, message = "значение не может быть меньше 1")
                                                        Long userId) {
        var postsByUser = postService.findByUserId(userId);
        if (postsByUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(postsByUser);
    }
}
