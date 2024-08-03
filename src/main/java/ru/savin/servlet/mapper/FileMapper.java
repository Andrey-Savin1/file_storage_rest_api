package ru.savin.servlet.mapper;

import ru.savin.servlet.dto.FileDto;
import ru.savin.servlet.model.File;

public  class FileMapper {

    public static FileDto toFileDto(File file){

        return FileDto.builder()
                .id(file.getId())
                .name(file.getName())
                .filePath(file.getFilePath())
                .build();

    }

    public static File toFile(FileDto fileDto){

        return File.builder()
                .id(fileDto.getId())
                .name(fileDto.getName())
                .filePath(fileDto.getFilePath())
                .build();
    }
}
