package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.Subscribe;
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

    @Autowired
    private SubscribeRepository subscribeRepository;

    @Autowired
    private FriendRepository friendRepository;

    @BeforeEach
    public void setUp() {
        friendRepository.deleteAll();
        subscribeRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        friendRepository.deleteAll();
        subscribeRepository.deleteAll();
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
    public void whenFindUserByLoginAndPassword_thenReturnUser() {
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
        var foundUser = userRepository.findByLoginAndPassword("testUs@test.com", "testUs");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user1);
    }

    @Test
    public void whenFindUserSubscriber_thenReturnListOfSubscribers() {
        var userSubscriber = new User();
        userSubscriber.setName("testUs");
        userSubscriber.setLogin("testUs@test.com");
        userSubscriber.setPassword("testUs");
        userRepository.save(userSubscriber);
        var userTo = new User();
        userTo.setName("testUs");
        userTo.setLogin("testUs1@test.com");
        userTo.setPassword("testUs1");
        userRepository.save(userTo);
        var subscriber = new Subscribe();
        subscriber.setUserTo(userTo);
        subscriber.setUserSubscriber(userSubscriber);
        subscribeRepository.save(subscriber);
        var findSubList = userRepository.findBySubscribe(userTo.getId());
        assertThat(findSubList).hasSize(1);
        assertThat(findSubList).containsExactlyInAnyOrder(userSubscriber);
    }

    @Test
    public void whenFindAllFriendsByUser() {
        var userOffer = new User();
        userOffer.setName("testOffer");
        userOffer.setLogin("testOffer@test.com");
        userOffer.setPassword("testOffer");
        userRepository.save(userOffer);
        var userAccept1 = new User();
        userAccept1.setName("testAccept1");
        userAccept1.setLogin("testAccept1@test.com");
        userAccept1.setPassword("testAccept1");
        userRepository.save(userAccept1);
        var userAccept2 = new User();
        userAccept2.setName("testAccept2");
        userAccept2.setLogin("testAccept2@test.com");
        userAccept2.setPassword("testAccept2");
        userRepository.save(userAccept2);
        var friend1 = new Friend();
        friend1.setUser(userOffer);
        friend1.setFriend(userAccept1);
        friend1.setStatus(true);
        friendRepository.save(friend1);
        var friend2 = new Friend();
        friend2.setUser(userOffer);
        friend2.setFriend(userAccept2);
        friend2.setStatus(false);
        friendRepository.save(friend2);
        var userFriends = userRepository.findFriendsByUserId(userOffer.getId());
        assertThat(userFriends).containsExactlyInAnyOrder(userAccept1);
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