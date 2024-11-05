package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmedia.model.Friend;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FriendRepositoryTest {

    @Autowired
    private FriendRepository friendRepository;

    @BeforeEach
    public void setUp() {
        friendRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        friendRepository.deleteAll();
    }

    @Test
    public void whenAddFriend_thenReturnFriend() {
        var friend = new Friend();
        friendRepository.save(friend);
        var result = friendRepository.findById(friend.getId());
        assertThat(result).isNotNull();
    }

    @Test
    public void whenDeleteFriend_thenReturnFriend() {
        var friend = new Friend();
        friendRepository.save(friend);
        var result = friendRepository.findById(friend.getId());
        assertThat(result).isNotNull();
        friendRepository.delete(friend);
        result = friendRepository.findById(friend.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void whenFindAllFriends_thenReturnFriends() {
        var friend1 = new Friend();
        friendRepository.save(friend1);
        var friend2 = new Friend();
        friendRepository.save(friend2);
        var friend3 = new Friend();
        friendRepository.save(friend3);
        var foundFriends = friendRepository.findAll();
        assertThat(foundFriends).hasSize(3);
        assertThat(foundFriends).isEqualTo(List.of(friend1, friend2, friend3));
    }

    @Test
    public void whenFindFriendById_thenReturnFriend() {
        var friend1 = new Friend();
        friendRepository.save(friend1);
        var foundFriend = friendRepository.findById(friend1.getId());
        assertThat(foundFriend).isPresent();
        assertThat(foundFriend.get().getId()).isEqualTo(friend1.getId());
    }
}