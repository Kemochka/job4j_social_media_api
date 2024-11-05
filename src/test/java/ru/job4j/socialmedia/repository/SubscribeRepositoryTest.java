package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmedia.model.Subscribe;
import ru.job4j.socialmedia.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubscribeRepositoryTest {
    @Autowired
    private SubscribeRepository subscribeRepository;

    @BeforeEach
    public void setUp() {
        subscribeRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        subscribeRepository.deleteAll();
    }

    @Test
    public void whenAddSubscribe_ThenGetSubscribe() {
        var subscribe = new Subscribe();
        subscribeRepository.save(subscribe);
        var foundSubscribe = subscribeRepository.findById(subscribe.getId());
        assertThat(foundSubscribe).isPresent();
    }

    @Test
    public void whenDeleteSubscribe_ThenGetSubscribe() {
        var subscribe = new Subscribe();
        subscribeRepository.save(subscribe);
        var foundSubscribe = subscribeRepository.findById(subscribe.getId());
        assertThat(foundSubscribe).isPresent();
        subscribeRepository.delete(foundSubscribe.get());
        assertThat(subscribeRepository.findAll()).isEmpty();
    }

    @Test
    public void whenFindAllSubscribes_ThenGetSubscribes() {
        var subscribe1 = new Subscribe();
        var subscribe2 = new Subscribe();
        var subscribe3 = new Subscribe();
        subscribeRepository.save(subscribe1);
        subscribeRepository.save(subscribe2);
        subscribeRepository.save(subscribe3);
        var foundSubscribe = subscribeRepository.findAll();
        assertThat(foundSubscribe).hasSize(3);
        assertThat(foundSubscribe).isEqualTo(List.of(subscribe1, subscribe2, subscribe3));
    }

    @Test
    public void whenFindSubscribeById_ThenGetSubscribe() {
        var subscribe1 = new Subscribe();
        subscribeRepository.save(subscribe1);
        var foundSubscribe = subscribeRepository.findById(subscribe1.getId());
        assertThat(foundSubscribe).isPresent();
        assertThat(foundSubscribe.get().getId()).isEqualTo(subscribe1.getId());
    }
}