package ru.job4j.socialmedia.service.subscribe;

import ru.job4j.socialmedia.model.Subscribe;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

public interface SubscribeService {
    Subscribe save(Subscribe subscribe);

    Optional<Subscribe> findById(Long id);

    List<Subscribe> findAll();

    void deleteById(Long id);

    void createSubscribe(User userSubscriber, User userTo);

    void deleteSubscribe(User user, Subscribe subscribe);

    Optional<Subscribe> findSubscribeByUserId(Long userSubscriberId, Long userToId);

}
