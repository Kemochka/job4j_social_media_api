package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.service.post.PostService;

import java.util.List;

@Tag(name = "PostsController", description = "PostsController management APIs")
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private final PostService postService;

    @Operation(
            summary = "Retrieve all posts",
            description = "Get a list of Posts objects . The response is Posts objects with title, description, created and user with userId, login, password and name.",
            tags = {"Posts", "findAll"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Post> findAll() {
        return postService.findAll();
    }

    @Operation(
            summary = "Retrieve all posts by userId",
            description = "Get a list of Posts objects by userId. The response is list of Posts objects with postId, title, description, created and user with userId, login, password and name.",
            tags = {"Posts", "findPostsByUserId"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Post.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
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
