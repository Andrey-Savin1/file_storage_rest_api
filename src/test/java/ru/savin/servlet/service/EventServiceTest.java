package ru.savin.servlet.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.savin.servlet.model.Event;
import ru.savin.servlet.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EventServiceTest {

    private final EventRepository eventRepository = Mockito.mock(EventRepository.class);
    private final EventService eventService = new EventService(eventRepository);

    Event event = Event.builder()
            .id(1L)
            .build();

    @Test
    void createEvent() {
        when(eventRepository.create(any())).thenReturn(event);
        var res = eventService.createEvent(event);

        assertEquals(res.getId(), 1L);
    }

    @Test
    void getEventById() {
        when(eventRepository.getById(any())).thenReturn(event);

        var result = eventRepository.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getAllEvent() {

        List<Event> event = new ArrayList<>();
        event.add(Event.builder()
                .id(2L)
                .build());

        when(eventRepository.getAll()).thenReturn(event);
        var result = eventService.getAllEvent();

        assertEquals(2L, result.get(0).getId());
    }
}