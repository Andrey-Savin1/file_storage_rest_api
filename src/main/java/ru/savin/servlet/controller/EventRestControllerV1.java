package ru.savin.servlet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.savin.servlet.mapper.EventMapper;
import ru.savin.servlet.repository.Impl.EventRepositoryImpl;
import ru.savin.servlet.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EventRestController", urlPatterns = "/api/v1/events/*")
public class EventRestControllerV1 extends HttpServlet {


    private final EventService eventService = new EventService(new EventRepositoryImpl());
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (req.getPathInfo() == null) {
            var allEvent = eventService.getAllEvent();
            var rs = allEvent.stream().map(EventMapper::toEventDto).toList();
            objectMapper.writeValue(resp.getOutputStream(), rs);
        } else {
            var id = Long.valueOf(req.getPathInfo().replace("/", ""));
            var eventById = eventService.getEventById(id);
            if (eventById != null) {
                objectMapper.writeValue(resp.getOutputStream(), EventMapper.toEventDto(eventById));
            } else objectMapper.writeValue(resp.getOutputStream(), null);
        }
    }
}
