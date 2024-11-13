package ru.job4j.socialmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.socialmedia.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private Long id;
    private LocalDateTime created = LocalDateTime.now();
    private User userFrom;
    private User userTo;
    boolean accept;
}
