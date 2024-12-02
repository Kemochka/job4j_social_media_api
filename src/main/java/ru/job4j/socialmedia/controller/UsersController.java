package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.dto.UserDto;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.service.user.UserService;

import java.util.List;

@Tag(name = "UsersController", description = "UsersController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;

    @Operation(
            summary = "Retrieve all users",
            description = "Get a list of Users objects . The response is Users objects with userId, login, password and name.",
            tags = {"Users", "getAll"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<User> getAll() {
        return userService.findAll();
    }

    @Operation(
            summary = "Retrieve all posts by userId",
            description = "Get a list of UserDtos objects by userId. The response is UserDtos objects with userId, name and list of posts.",
            tags = {"Users", "getUsersWithPosts"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersWithPosts(@PathVariable List<Long> userId) {
        var usersWithPosts = userService.findUsersWithPostsList(userId);
        if (usersWithPosts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(usersWithPosts);
    }
}
