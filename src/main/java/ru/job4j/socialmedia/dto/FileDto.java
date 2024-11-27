package ru.job4j.socialmedia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    @Schema(description = "Name of image", example = "JetBrains IDE_mac.pdf")
    private String name;
    private byte[] content;
    @Schema(description = "Path of image", example = "'/Users/kemochka/Desktop/new/JetBrains IDE_mac.pdf'")
    private String path;

    public FileDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }
}
