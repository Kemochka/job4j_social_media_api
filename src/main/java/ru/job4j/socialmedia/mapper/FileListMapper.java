package ru.job4j.socialmedia.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.job4j.socialmedia.model.File;
import ru.job4j.socialmedia.repository.FileRepository;

import java.util.List;

@Named("FileListMapper")
@Component
@RequiredArgsConstructor
public class FileListMapper {
    private final FileRepository fileRepository;

    @Named("findFilesByPostId")
    public List<File> findFilesByPostId(Long postId) {
        return fileRepository.findAll().stream().filter(file -> file.getPost().getId().equals(postId)).toList();
    }
}
