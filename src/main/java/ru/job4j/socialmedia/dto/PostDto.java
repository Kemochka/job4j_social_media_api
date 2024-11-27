package ru.job4j.socialmedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.socialmedia.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    @NotEmpty(message = "заголовок не может быть пустым")
    @Schema(description = "Post title", example = "My new post")
    private String title;
    @Size(max = 255, message = "описание превышает допустимый размер текста 255 символов")
    @Schema(description = "Post description", example = "In this post I want to describe..")
    private String description;
    @Schema(description = "Post creation date")
    private LocalDateTime created;
    private User user;
    private List<FileDto> fileList = new ArrayList<>();
}
