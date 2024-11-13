package ru.job4j.socialmedia.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.job4j.socialmedia.dto.FileDto;
import ru.job4j.socialmedia.dto.PostDto;
import ru.job4j.socialmedia.model.File;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface FileMapper {
    FileDto getFileDtoFromFile(File file);

    File getFileFromDto(FileDto dto);

    default List<FileDto> getFileListDtoFromFileList(List<File> fileList) {
        return fileList.stream().map(this::getFileDtoFromFile).toList();
    }

    default List<File> getFileListFromDtoList(PostDto postDto) {
        return postDto.getFileList().stream().map(this::getFileFromDto).toList();
    }
}
