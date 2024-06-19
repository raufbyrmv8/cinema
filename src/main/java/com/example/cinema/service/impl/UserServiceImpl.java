package com.example.cinema.service.impl;

import com.example.cinema.dto.UserDto;
import com.example.cinema.entity.Ticket;
import com.example.cinema.entity.User;
import com.example.cinema.mapper.UserMapper;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    @Override
    public UserDto save(UserDto userDto) {
        User user = userMapper.INSTANCE.userDtoToUser(userDto);
        user.getTickets()
                .forEach(ticket -> ticket.setUser(user));
        List<Ticket> users = user.getTickets();
        user.setTickets(users);
        return userMapper.INSTANCE.userToUserDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> getUsers() {
        List<User>users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user,UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUsers(UserDto userDto, long id) {
        User user = userRepository.findById(id)
                .map(user1 -> modelMapper.map(user1,User.class))
                .orElseThrow(()-> new RuntimeException("User is not found"));
        modelMapper.map(userDto,user);
        return userMapper.INSTANCE.userToUserDto(userRepository.save(user));
    }

    @Override
    public void deleteUsers(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found id"));
        userRepository.delete(user);
    }

    @Override
    public Double getUserBalance(long userId) {
       return userRepository.findUserBalanceById(userId);
    }

}
