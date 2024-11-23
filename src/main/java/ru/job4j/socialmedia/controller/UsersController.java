package ru.job4j.socialmedia.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.dto.UserDto;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.service.user.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserDto>> getUsersWithPosts(@PathVariable List<Long> userId) {
        var usersWithPosts = userService.findUsersWithPostsList(userId);
        if (usersWithPosts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(usersWithPosts);
    }
}
