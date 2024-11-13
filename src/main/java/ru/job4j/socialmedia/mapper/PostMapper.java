package ru.job4j.socialmedia.mapper;

import org.mapstruct.Mapper;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.model.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto getModelFromEntity(Post post);

    Post getEntityFromDto(PostDto postDto);
}
