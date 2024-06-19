package com.example.cinema.controller;

import com.example.cinema.dto.UserDto;
import com.example.cinema.entity.User;
import com.example.cinema.service.TicketService;
import com.example.cinema.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

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

    @Test
    void save() throws Exception {
        when(userService.save(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/users/save")
                .contentType(APPLICATION_JSON)
                .content("{\"balance\":12.5}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(12.5));
        verify(userService,times(1)).save(any(UserDto.class));
    }

    @Test
    void getUsers() throws Exception {
        when(userService.getUsers()).thenReturn(List.of(userDto));

        mockMvc.perform(get("/users/all")
                .contentType(APPLICATION_JSON)
                .content("{\"balance\":12.5}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance").value(12.5));
        verify(userService,times(1)).getUsers();
    }

    @Test
    void updateUsers() throws Exception {
        long userId = 1L;
        when(userService.updateUsers(any(UserDto.class),anyLong())).thenReturn(userDto);

        mockMvc.perform(put("/users/updateUsers")
                        .param("id",String.valueOf(userId))
                        .contentType(APPLICATION_JSON)
                        .content("{\"balance\":12.5}"))
                        .andDo(print())
                        .andExpect(status().isOk())
                       .andExpect(jsonPath("$.balance").value(12.5));
        verify(userService,times(1)).updateUsers(any(UserDto.class),anyLong());
    }

    @Test
    void deleteUsers() throws Exception {
        long userId = 1L;

        mockMvc.perform(delete("/users/delete")
                .param("id",String.valueOf(userId))
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUsers(anyLong());
    }
    @Test
    void getBalance() throws Exception {
        long userId = 1L;
        when(userService.getUserBalance(anyLong())).thenReturn(userDto.getBalance());

        mockMvc.perform(get("/users/balance")
                .param("userId",String.valueOf(userId))
                .contentType(APPLICATION_JSON)
                .content("{\"balance\":12.5}"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userService,times(1)).getUserBalance(anyLong());

    }
}