package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenAddPost_thenReturnPost() {
        var post = new Post();
        post.setTitle("test");
        post.setDescription("test");
        post.setUser(testUser);
        postRepository.save(post);
        var foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get()).isEqualTo(post);
    }

    @Test
    public void whenFindPostsByUser_thenReturnList() {
        var user = new User();
        user.setLogin("test1");
        user.setPassword("test1");
        user.setName("test1");
        userRepository.save(user);
        var post = new Post();
        post.setTitle("test");
        post.setDescription("test");
        post.setUser(user);
        postRepository.save(post);
        var post1 = new Post();
        post1.setTitle("test1");
        post1.setDescription("test1");
        post1.setUser(user);
        postRepository.save(post1);
        var post2 = new Post();
        post2.setTitle("test2");
        post2.setDescription("test2");
        post2.setUser(user);
        postRepository.save(post2);
        var foundPosts = postRepository.findByUserId(user.getId());
        assertThat(foundPosts).hasSize(3);
    }

    @Test
    public void whenFindByCreatedIsGreaterThanEqualAndCreatedIsLessThanEqual_thenReturnList() {
        var user = new User();
        user.setLogin("test1");
        user.setPassword("test1");
        user.setName("test1");
        userRepository.save(user);
        var post = new Post();
        post.setTitle("test");
        post.setDescription("test");
        post.setUser(user);
        postRepository.save(post);
        var post1 = new Post();
        post1.setTitle("test1");
        post1.setDescription("test1");
        post1.setUser(user);
        postRepository.save(post1);
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        var foundPosts = postRepository.findByCreatedIsGreaterThanEqualAndCreatedIsLessThanEqual(start, end);
        assertThat(foundPosts).hasSize(2);
    }

    @Test
    public void whenFindByOrderByCreatedDesc_thenReturnList() {
        var user = new User();
        user.setLogin("test1");
        user.setPassword("test1");
        user.setName("test1");
        userRepository.save(user);
        var post = new Post();
        post.setTitle("test");
        post.setDescription("test");
        post.setUser(user);
        postRepository.save(post);
        var post1 = new Post();
        post1.setTitle("test1");
        post1.setDescription("test1");
        post1.setUser(user);
        postRepository.save(post1);
        assertThat(postRepository.findByOrderByCreatedDesc(Pageable.ofSize(1))
                .getTotalElements()).isEqualTo(2);
    }

    @Test
    public void whenFindAllPosts_thenReturnAllPosts() {
        var post1 = new Post();
        post1.setTitle("test1");
        post1.setDescription("test1");
        post1.setUser(testUser);
        var post2 = new Post();
        post2.setTitle("test2");
        post2.setDescription("test2");
        post2.setUser(testUser);
        var post3 = new Post();
        post3.setTitle("test3");
        post3.setDescription("test3");
        post3.setUser(testUser);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        var foundPosts = postRepository.findAll();
        assertThat(foundPosts).hasSize(3);
        assertThat(foundPosts).isEqualTo(List.of(post1, post2, post3));
    }

    @Test
    public void whenFindPostById_thenReturnPost() {
        var post = new Post();
        post.setTitle("test1");
        post.setDescription("test1");
        post.setUser(testUser);
        postRepository.save(post);
        var foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getId()).isEqualTo(post.getId());
    }

    @Test
    public void whenDeletePost_thenReturnPost() {
        var post = new Post();
        post.setTitle("test1");
        post.setDescription("test1");
        post.setUser(testUser);
        postRepository.save(post);
        var foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isPresent();
        postRepository.delete(foundPost.get());
        assertThat(postRepository.findAll()).isEmpty();
    }
}