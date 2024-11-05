package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmedia.model.Message;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void setUp() {
        messageRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        messageRepository.deleteAll();
    }

    @Test
    public void whenSaveMessage_thenReturnMessage() {
        var message = new Message();
        message.setAccepted(true);
        messageRepository.save(message);
        var result = messageRepository.findById(message.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(message);
    }

    @Test
    public void whenDeleteMessage_thenReturnMessage() {
        var message = new Message();
        message.setAccepted(true);
        messageRepository.save(message);
        var result = messageRepository.findById(message.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(message);
        messageRepository.delete(message);
        result = messageRepository.findById(message.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void whenFindById_thenReturnMessage() {
        var message = new Message();
        message.setAccepted(true);
        messageRepository.save(message);
        var result = messageRepository.findById(message.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(message.getId());
    }

    @Test
    public void whenFindAllMessages_thenReturnMessages() {
        var message1 = new Message();
        message1.setAccepted(true);
        messageRepository.save(message1);
        var message2 = new Message();
        message2.setAccepted(true);
        messageRepository.save(message2);
        var foundMessages = messageRepository.findAll();
        assertThat(foundMessages).isNotEmpty();
        assertThat(foundMessages).isEqualTo(List.of(message1, message2));
    }
}