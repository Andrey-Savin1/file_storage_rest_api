package ru.savin.servlet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.savin.servlet.dto.UserDto;
import ru.savin.servlet.mapper.UserMapper;
import ru.savin.servlet.model.User;
import ru.savin.servlet.repository.Impl.UserRepositoryImpl;
import ru.savin.servlet.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "UserRestController", urlPatterns = "/api/v1/users/*")
public class UserRestControllerV1 extends HttpServlet {

    private final UserService userService = new UserService(new UserRepositoryImpl());
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (req.getPathInfo() == null) {
            var users = userService.getAllUsers();
            var rs = users.stream().map(UserMapper::toUserDtoAndEvents).toList();
            objectMapper.writeValue(resp.getOutputStream(), rs);
        } else {
            var id = Long.valueOf(req.getPathInfo().replace("/", ""));
            var user = userService.getUserById(id);
            if (user != null){
                objectMapper.writeValue(resp.getOutputStream(),UserMapper.toUserDtoAndEvents(user));
            }else objectMapper.writeValue(resp.getOutputStream(),null);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        var newUser = objectMapper.readValue(req.getInputStream(), UserDto.class);
        var result = userService.createUser(User.builder().name(newUser.getName()).build());
        objectMapper.writeValue(resp.getOutputStream(), UserMapper.toUserDto(result));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        var id = req.getPathInfo().replace("/", "");
        var flag = userService.getAllUsers().stream().anyMatch(t -> t.getId().equals(Long.valueOf(id)));
        if (!flag) {
            resp.setStatus(404);
        } else {
            userService.deleteUser(Long.valueOf(id));
            resp.setStatus(200);
        }
    }
}
