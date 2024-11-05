package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmedia.model.File;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private Post post;
    private User user;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        fileRepository.deleteAll();
    }

    @AfterAll
    public void clearAll() {
        postRepository.deleteAll();
        fileRepository.deleteAll();
    }

    @Test
    public void whenSaveFile_thenReturnFile() {
        var file = new File();
        file.setName("test");
        file.setPath("test");
        file.setPost(post);
        fileRepository.save(file);
        assertThat(fileRepository.findAll()).hasSize(1);
        assertThat(fileRepository.findById(file.getId())).isNotNull();
    }

    @Test
    public void whenDeleteFile_thenReturnFile() {
        var file = new File();
        file.setName("test");
        file.setPath("test");
        file.setPost(post);
        fileRepository.save(file);
        fileRepository.delete(file);
        assertThat(fileRepository.findById(file.getId())).isEmpty();
    }

    @Test
    public void whenFindAllFiles_thenReturnFiles() {
        var file1 = new File();
        file1.setName("test1");
        file1.setPath("test1");
        file1.setPost(post);
        fileRepository.save(file1);
        var file2 = new File();
        file2.setName("test2");
        file2.setPath("test2");
        file2.setPost(post);
        fileRepository.save(file2);
        var foundFiles = fileRepository.findAll();
        assertThat(foundFiles).hasSize(2);
        assertThat(foundFiles).isEqualTo(List.of(file1, file2));
    }

    @Test
    public void whenFindFileById_thenReturnFile() {
        var file1 = new File();
        file1.setName("test1");
        file1.setPath("test1");
        file1.setPost(post);
        fileRepository.save(file1);
        var foundFile = fileRepository.findById(file1.getId());
        assertThat(foundFile).isPresent();
        assertThat(foundFile.get().getId()).isEqualTo(file1.getId());
    }

}