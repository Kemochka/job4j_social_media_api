package ru.job4j.socialmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.socialmedia.model.Friend;
import ru.job4j.socialmedia.model.Subscribe;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendSubscribesDto {
    private Friend friend;
    private Subscribe offer;
    private Subscribe accepted;
}
