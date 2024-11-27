package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "PostController", description = "PostController management APIs")
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @Operation(
            summary = "Retrieve a Post by postId",
            description = "Get a Post object by specifying its postId. The response is Post object with title, description, created and user with userId, login, password and name.",
            tags = {"Post", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Post.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable("postId")
                                    @NotNull
                                    @Min(value = 1, message = "значение не может быть меньше 1")
                                    Long postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Saving PostDto",
            description = "Saving PostDto by specifying title, description, created and userId. The response is Post object with postId, description, created and user",
            tags = {"Post", "save"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Post.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
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

    @Operation(
            summary = "Updating post",
            description = "Updating post by specifying title and description. The response is http-status 200 OK",
            tags = {"Post", "update"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody PostDto post) {
        if (!postService.updateFormDto(post)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Updating post",
            description = "Partial updating post by title and description. The response is http-status 200 OK",
            tags = {"Post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@Valid @RequestBody PostDto post) {
        postService.updateFormDto(post);
    }

    @Operation(
            summary = "Delete post",
            description = "Removing a post by his ID. The response is no content",
            tags = {"Post", "delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeById(@PathVariable long postId) {
        if (!postService.deleteById(postId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
        return ResponseEntity.noContent().build();
    }
}
