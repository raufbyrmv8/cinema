package com.example.cinema.service.impl;

import com.example.cinema.dto.UserDto;
import com.example.cinema.entity.User;
import com.example.cinema.mapper.UserMapper;
import com.example.cinema.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = User
                .builder()
                .balance(12.5)
                .build();
        userDto = UserDto
                .builder()
                .balance(12.5)
                .build();
    }

    @AfterEach
    void tearDown() {
        user = null;
        userDto = null;
    }

    @Test
    void save() {
    }

    @Test
    void getUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);
        List<UserDto>userDtoList = userService.getUsers();
        verify(userRepository,times(1)).findAll();
        verify(modelMapper,times(1)).map(user,UserDto.class);
    }

    @Test
    void updateUsers() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(modelMapper.map(user,User.class)).thenReturn(user);
        doNothing().when(modelMapper).map(userDto,user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto userDtoRes = userService.updateUsers(userDto, userId);
        verify(userRepository,times(1)).findById(userId);
        verify(modelMapper,times(1)).map(user,User.class);
        verify(modelMapper,times(1)).map(userDto,user);
        verify(userRepository, times(1)).save(user);
        assertNotNull(userDtoRes);
        assertEquals(userDto.getBalance(), userDtoRes.getBalance());
    }

    @Test
    void deleteUsers() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.deleteUsers(userId);
        verify(userRepository,times(1)).delete(user);
    }

    @Test
    void getUserBalance() {
        long userId = 1L;
        when(userRepository.findUserBalanceById(userId)).thenReturn(userDto.getBalance());
        Double balance = userService.getUserBalance(userId);
        assertNotNull(balance);
        verify(userRepository,times(1)).findUserBalanceById(anyLong());
    }

}