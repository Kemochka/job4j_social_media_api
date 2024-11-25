package ru.job4j.socialmedia.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.service.post.PostService;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable("postId")
                                    @NotNull
                                    @Min(value = 1, message = "значение не может быть меньше 1")
                                    Long postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostDto> save(@RequestBody PostDto post) {
        postService.create(post);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(post);
    }

    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody PostDto post) {
        if (!postService.updateFormDto(post)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@Valid @RequestBody PostDto post) {
        postService.updateFormDto(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeById(@PathVariable long postId) {
        if (!postService.deleteById(postId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
        return ResponseEntity.noContent().build();
    }
}
