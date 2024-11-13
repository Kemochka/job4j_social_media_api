package ru.job4j.socialmedia.service.friend;

import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.Request;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

public interface FriendService {
    Friend save(Friend friend);

    List<Friend> findAll();

    Optional<Friend> findById(Long id);

    void deleteById(Long id);

    void createFriend(User userOffer, User userAccept);

    void deleteFriend(User user, Friend friend);

    void createFriendAndSubscribeFromRequest(Request request);
}
