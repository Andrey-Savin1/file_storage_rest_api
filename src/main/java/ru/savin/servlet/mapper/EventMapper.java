package ru.savin.servlet.mapper;

import ru.savin.servlet.dto.EventDto;
import ru.savin.servlet.model.Event;

public class EventMapper {

    public static EventDto toEventDto(Event event) {

        return EventDto.builder()
                .id(event.getId())
                .fileDto(FileMapper.toFileDto(event.getFile()))
                .build();
    }

    public static Event toEvent(EventDto eventDto) {

        return Event.builder()
                .id(eventDto.getId())
                .file(FileMapper.toFile(eventDto.getFileDto()))
                .build();
    }
}
