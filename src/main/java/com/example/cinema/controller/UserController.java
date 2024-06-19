package com.example.cinema.controller;

import com.example.cinema.dto.UserDto;
import com.example.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping("/save")
    public UserDto save(@RequestBody UserDto userDto){
        return userService.save(userDto);
    }
    @GetMapping("/all")
    public List<UserDto>getUsers(){
        return userService.getUsers();
    }
    @PutMapping("/updateUsers")
    public UserDto updateUsers(@RequestBody UserDto userDto, @Param("id") long id){
        return userService.updateUsers(userDto,id);
    }
    @DeleteMapping("/delete")
    public void deleteUsers(@Param("id") long id){
        userService.deleteUsers(id);
    }
    @GetMapping("/balance")
    public Double getBalance(@RequestParam("userId") long userId){
        return userService.getUserBalance(userId);
    }
}
