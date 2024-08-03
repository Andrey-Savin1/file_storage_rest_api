package ru.savin.servlet.mapper;

import ru.savin.servlet.dto.UserDto;
import ru.savin.servlet.model.User;

public  class UserMapper {

    public static UserDto toUserDtoAndEvents(User user){

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .eventList(user.getEvents().stream().map(EventMapper::toEventDto).toList())
                .build();

    }

    public static UserDto toUserDto(User user){

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();

    }

    public static User toUser(UserDto userDto){

        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .build();

    }
}
