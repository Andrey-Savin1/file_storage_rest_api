package ru.savin.servlet.service;

import lombok.RequiredArgsConstructor;
import ru.savin.servlet.model.Event;
import ru.savin.servlet.model.File;
import ru.savin.servlet.repository.EventRepository;

import java.util.List;

@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;


    public Event createEvent(Event event) {
        return eventRepository.create(event);
    }

    public Event getEventById(Long id) {
        return eventRepository.getById(id);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Event updateEvent(Long id) {

        Event event = Event.builder()
                .id(id)
                .file(File.builder().build())
                .build();

        return eventRepository.update(event);
    }

    public List<Event> getAllEvent() {
        return eventRepository.getAll();
    }

}
