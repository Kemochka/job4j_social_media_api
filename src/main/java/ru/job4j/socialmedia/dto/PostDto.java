package ru.job4j.socialmedia.dto;

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
    private String title;
    private String description;
    private LocalDateTime created;
    private User user;
    private List<FileDto> fileList = new ArrayList<>();
}
