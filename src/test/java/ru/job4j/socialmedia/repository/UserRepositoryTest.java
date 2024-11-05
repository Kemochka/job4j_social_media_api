package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmedia.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void whenAddUser_thenReturnUser() {
        var user = new User();
        user.setName("testUs");
        user.setLogin("testUs@test.com");
        user.setPassword("testUs");
        userRepository.save(user);
        var foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("testUs");
    }

    @Test
    public void whenFindAllUsers_thenReturnAllUsers() {
        var user1 = new User();
        user1.setName("testUs");
        user1.setLogin("testUs@test.com");
        user1.setPassword("testUs");
        userRepository.save(user1);
        var user2 = new User();
        user2.setName("testUs1");
        user2.setLogin("testUs1@test.com");
        user2.setPassword("testUs1");
        userRepository.save(user2);
        userRepository.findAll();
        var foundUsers = userRepository.findAll();
        assertThat(foundUsers).isEqualTo(List.of(user1, user2));
    }

    @Test
    public void whenFindUserById_thenReturnUser() {
        var user = new User();
        user.setName("testUs");
        user.setLogin("testUs@test.com");
        user.setPassword("testUs");
        userRepository.save(user);
        var foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(user.getId());
    }

    @Test
    public void whenDeleteUser_thenReturnUser() {
        var user = new User();
        user.setName("testUs");
        user.setLogin("testUs@test.com");
        user.setPassword("testUs");
        userRepository.save(user);
        var foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        userRepository.deleteById(user.getId());
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }
}