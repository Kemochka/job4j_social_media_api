package ru.job4j.socialmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private String name;
    private byte[] content;
    private String path;

    public FileDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }
}
