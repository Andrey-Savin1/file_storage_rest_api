package ru.savin.servlet.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.savin.servlet.model.User;
import ru.savin.servlet.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {


    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    User user = User.builder()
            .id(1L)
            .name("test")
            .build();

    @Test
    void createUser() {
        when(userRepository.create(any())).thenReturn(user);

        var res = userService.createUser(user);

        assertEquals(res.getName(), "test");
        assertEquals(res.getId(), 1L);
    }

    @Test
    void getUserById() {

        when(userRepository.getById(any())).thenReturn(user);

        var result = userService.getUserById(1L);

        assertEquals("test", result.getName());
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteUser() {

        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAllUsers() {

        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .id(2L)
                .name("test2")
                .build());

        when(userRepository.getAll()).thenReturn(users);
        var result = userService.getAllUsers();

        assertEquals("test2", result.get(0).getName());
        assertEquals(2L, result.get(0).getId());
    }
}