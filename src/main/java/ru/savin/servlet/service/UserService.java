package ru.savin.servlet.service;

import lombok.RequiredArgsConstructor;
import ru.savin.servlet.model.User;
import ru.savin.servlet.repository.UserRepository;


import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User createUser(User user) {
        return userRepository.create(user);
    }

    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, String name) {

        User label = User.builder().id(id).name(name).build();

        return userRepository.update(label);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }
}
