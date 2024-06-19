package com.example.cinema.service;

import com.example.cinema.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto);
    List<UserDto>getUsers();
    UserDto updateUsers(UserDto userDto, long id);
    void deleteUsers(long id);
    Double getUserBalance(long userId);

}
