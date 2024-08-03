package ru.savin.servlet.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.savin.servlet.model.File;
import ru.savin.servlet.repository.FileRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileServiceTest {

    private final FileRepository fileRepository = Mockito.mock(FileRepository.class);
    private final FileService fileService = new FileService(fileRepository);

    File file = File.builder()
            .id(1L)
            .name("test")
            .build();

    @Test
    void createFile() {
        when(fileRepository.create(any())).thenReturn(file);

        var res = fileService.createFile(file);

        assertEquals(res.getName(), "test");
        assertEquals(res.getId(), 1L);
    }

    @Test
    void getFileById() {

        when(fileRepository.getById(any())).thenReturn(file);

        var result = fileService.getFileById(1L);

        assertEquals("test", result.getName());
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteFile() {
        fileService.deleteFile(1L);
        verify(fileRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAllFiles() {
        List<File> files = new ArrayList<>();
        files.add(File.builder()
                .id(2L)
                .name("test2")
                .build());

        when(fileRepository.getAll()).thenReturn(files);
        var result = fileService.getAllFiles();

        assertEquals("test2", result.get(0).getName());
        assertEquals(2L, result.get(0).getId());
    }
}