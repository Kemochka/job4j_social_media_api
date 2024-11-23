package ru.job4j.socialmedia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.model.Post;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FileMapper.class, FileListMapper.class})
public interface PostMapper {
    @Mapping(target = "fileList", qualifiedByName = {"FileListMapper", "findFilesByPostId"}, source = "post.id")
    PostDto getModelFromEntity(Post post);

    Post getEntityFromDto(PostDto postDto);

    default List<PostDto> getPostsDtoFromPostList(List<Post> posts) {
        return posts.stream().map(this::getModelFromEntity).toList();
    }
}
