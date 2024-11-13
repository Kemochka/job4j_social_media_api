package ru.job4j.socialmedia.service.user;

import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findById(Long userId);

    Optional<User> findByLoginAndPassword(String login, String password);

    List<User> findAll();

    void deleteById(Long userId);

    List<User> findAllSubscribersById(Long userId);

    List<User> findAllFriendsById(Long userId);
}
