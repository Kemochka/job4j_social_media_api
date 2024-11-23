package ru.job4j.socialmedia.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.repository.PostRepository;

import java.util.List;

@Named("PostListMapper")
@Component
@RequiredArgsConstructor
public class PostListMapper {
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    @Named("findPostsByUserId")
    public List<PostDto> findPostsByUserId(Long userId) {
        return postMapper.getPostsDtoFromPostList(postRepository.findByUserId(userId));
    }
}
