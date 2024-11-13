package ru.job4j.socialmedia.service.subscribe;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Subscribe;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.SubscribeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleSubscribeService implements SubscribeService {
    private final SubscribeRepository subscribeRepository;

    @Override
    public Subscribe save(Subscribe subscribe) {
        return subscribeRepository.save(subscribe);
    }

    @Override
    public Optional<Subscribe> findById(Long id) {
        return subscribeRepository.findById(id);
    }

    @Override
    public List<Subscribe> findAll() {
        return subscribeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        subscribeRepository.deleteSubscribeById(id);
    }

    @Transactional
    @Override
    public void createSubscribe(User userSubscriber, User userTo) {
        var newSubscribe = new Subscribe();
        newSubscribe.setUserSubscriber(userSubscriber);
        newSubscribe.setUserTo(userTo);
        subscribeRepository.save(newSubscribe);
    }

    @Transactional
    @Override
    public void deleteSubscribe(User user, Subscribe subscribe) {
        if (subscribeRepository.findByUserSubscriberId(user.getId()).contains(subscribe)) {
            subscribeRepository.delete(subscribe);
        }
    }

    @Transactional
    @Override
    public Optional<Subscribe> findSubscribeByUserId(Long userSubscriberId, Long userToId) {
        return subscribeRepository.findSubscribeByUserSubscriberIdAndIdAndUserToId(userSubscriberId, userToId);
    }
}
