package ru.job4j.socialmedia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.socialmedia.dto.UserDto;
import ru.job4j.socialmedia.model.User;

@Mapper(componentModel = "spring", uses = PostListMapper.class)
public interface UserMapper {
    @Mapping(target = "posts", qualifiedByName = {"PostListMapper", "findPostsByUserId"}, source = "user.id")
    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
