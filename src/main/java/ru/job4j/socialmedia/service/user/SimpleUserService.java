package ru.job4j.socialmedia.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.dto.UserDto;
import ru.job4j.socialmedia.mapper.UserMapper;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.UserRepository;
import ru.job4j.socialmedia.service.post.PostService;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PostService postService;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDto> findUsersWithPostsList(List<Long> useIds) {
        return useIds.stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(userMapper::userToUserDto)
                .toList();
    }

    @Override
    public boolean deleteById(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Post> posts = postService.findByUserId(userId);
        for (Post post : posts) {
            postService.deletePostByUser(user, post);
        }
        return userRepository.delete(userId) > 0L;
    }

    @Override
    public boolean update(User user) {
        return userRepository.update(user) > 0L;
    }

    @Override
    public List<User> findAllSubscribersById(Long userId) {
        return userRepository.findBySubscribe(userId);
    }

    @Override
    public List<User> findAllFriendsById(Long userId) {
        return userRepository.findFriendsByUserId(userId);
    }
}
