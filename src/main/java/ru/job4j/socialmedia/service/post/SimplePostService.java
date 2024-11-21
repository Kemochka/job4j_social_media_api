package ru.job4j.socialmedia.service.post;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.mapper.FileMapper;
import ru.job4j.socialmedia.mapper.PostMapper;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.FileRepository;
import ru.job4j.socialmedia.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final FileRepository fileRepository;
    private final PostMapper postMapper;
    private final FileMapper fileMapper;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    @Override
    public Post create(PostDto postDto) {
        var newPost = postMapper.getEntityFromDto(postDto);
        postRepository.save(newPost);
        System.out.println("Post saved with ID: " + newPost.getId());
        var fileList = fileMapper.getFileListFromDtoList(postDto);
        fileList.forEach(file -> file.setPost(newPost));
        fileRepository.saveAll(fileList);
        return newPost;
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findByToday(LocalDateTime today) {
        return postRepository.findByCreatedIsGreaterThanEqualAndCreatedIsLessThanEqual(today, LocalDateTime.now());
    }

    @Override
    public List<Post> findByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public Page<Post> findByOrderByCreatedDesc(Pageable pageable) {
        return postRepository.findByOrderByCreatedDesc(pageable);
    }

    @Override
    public Page<Post> findAllSubscriberPostsByUserOrderByCreatedDesc(Long id, Pageable pageable) {
        return postRepository.findAllSubscriberPostsByUserOrderByCreatedDesc(id, pageable);
    }

    @Override
    public boolean updateTitleAndDescription(Long postId, String title, String description) {
        return postRepository.updateTitleAndDesc(title, description, postId) > 0;
    }

    @Transactional
    @Override
    public boolean updatePostByUser(User user, Post post) {
        boolean result = false;
        if (postRepository.findByUserId(user.getId()).contains(post)) {
            postRepository.updateTitleAndDesc(post.getTitle(), post.getDescription(), post.getId());
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(Post post) {
        return postRepository.updateTitleAndDesc(post.getTitle(), post.getDescription(), post.getId()) > 0L;
    }

    @Override
    public boolean updateFormDto(PostDto postDto) {
        var newPost = postMapper.getEntityFromDto(postDto);
        return postRepository.updateTitleAndDesc(newPost.getTitle(), newPost.getDescription(), newPost.getId()) > 0L;
    }

    @Override
    public boolean deleteById(Long postId) {
        postRepository.deleteFileByPostId(postId);
        return postRepository.deletePostById(postId) > 0;
    }

    @Transactional
    @Override
    public boolean deletePostByUser(User user, Post post) {
        boolean result = false;
        if (postRepository.findByUserId(user.getId()).contains(post)) {
            var id = post.getId();
            postRepository.deleteFileByPostId(id);
            postRepository.deleteById(id);
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteFileByPostId(Long postId) {
        return postRepository.deleteFileByPostId(postId) > 0;
    }
}
