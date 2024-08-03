package ru.savin.servlet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.savin.servlet.dto.FileDto;
import ru.savin.servlet.mapper.FileMapper;
import ru.savin.servlet.model.Event;
import ru.savin.servlet.repository.Impl.EventRepositoryImpl;
import ru.savin.servlet.repository.Impl.FileRepositoryImpl;
import ru.savin.servlet.repository.Impl.UserRepositoryImpl;
import ru.savin.servlet.service.EventService;
import ru.savin.servlet.service.FileService;
import ru.savin.servlet.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "FileRestController", urlPatterns = "/api/v1/files/*")
public class FileRestControllerV1 extends HttpServlet {

    private final FileService fileService = new FileService(new FileRepositoryImpl());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService = new UserService(new UserRepositoryImpl());
    private final EventService eventService = new EventService(new EventRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (req.getPathInfo() == null) {
            var files = fileService.getAllFiles();
            var rs = files.stream().map(FileMapper::toFileDto).toList();
            objectMapper.writeValue(resp.getOutputStream(), rs);
        } else {
            var id = Long.valueOf(req.getPathInfo().replace("/", ""));
            var file = fileService.getFileById(id);
            if (file != null){
                objectMapper.writeValue(resp.getOutputStream(),FileMapper.toFileDto(file));
            }else objectMapper.writeValue(resp.getOutputStream(),null);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            var userId = Long.valueOf(req.getHeader("userId"));
            var savedResult = fileService.saveFileToPath(req);
            var user = userService.getUserById(userId);
            var resultFile = fileService.createFile(FileMapper.toFile(savedResult));

            eventService.createEvent(Event.builder().file(resultFile).user(user).build());
            objectMapper.writeValue(resp.getOutputStream(), resultFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        var id = Long.parseLong(req.getPathInfo().replace("/", ""));
        var flag = fileService.getAllFiles().stream().anyMatch(t -> t.getId().equals(id));
        if (!flag) {
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(404);
        } else {
            fileService.deleteFile(id);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(200);
        }
    }
}
