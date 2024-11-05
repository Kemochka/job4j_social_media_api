package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmedia.model.Event;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventRepositoryTest {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    private User testUser;
    private Post testPost;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        eventRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        eventRepository.deleteAll();
    }

    @Test
    public void whenSaveEvent_thenReturnEvent() {
        var event = new Event();
        event.setUser(testUser);
        event.setPost(testPost);
        eventRepository.save(event);
        var foundEvent = eventRepository.findById(event.getId());
        assertThat(foundEvent).isPresent();
        assertThat(event.getId()).isEqualTo(foundEvent.get().getId());
    }

    @Test
    public void whenDeleteEvent_thenReturnEvent() {
        var event = new Event();
        event.setUser(testUser);
        event.setPost(testPost);
        eventRepository.save(event);
        var foundEvent = eventRepository.findById(event.getId());
        assertThat(foundEvent).isPresent();
        eventRepository.delete(foundEvent.get());
        assertThat(eventRepository.findAll()).isEmpty();
    }

    @Test
    public void whenFindAllEvents_thenReturnEvents() {
        var event = new Event();
        event.setUser(testUser);
        event.setPost(testPost);
        eventRepository.save(event);
        var event1 = new Event();
        event1.setUser(testUser);
        event1.setPost(testPost);
        eventRepository.save(event1);
        var foundEvents = eventRepository.findAll();
        assertThat(foundEvents).isEqualTo(List.of(event, event1));
    }

    @Test
    public void whenFindEventById_thenReturnEvent() {
        var event = new Event();
        event.setUser(testUser);
        event.setPost(testPost);
        eventRepository.save(event);
        var foundEvent = eventRepository.findById(event.getId());
        assertThat(foundEvent).isPresent();
        assertThat(event.getId()).isEqualTo(foundEvent.get().getId());
    }

}