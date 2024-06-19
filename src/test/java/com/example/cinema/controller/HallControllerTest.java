package com.example.cinema.controller;

import com.example.cinema.dto.HallDto;
import com.example.cinema.dto.SeatDto;
import com.example.cinema.dto.SessionDto;
import com.example.cinema.entity.Hall;
import com.example.cinema.entity.Seat;
import com.example.cinema.entity.Session;
import com.example.cinema.service.HallService;
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
class HallControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HallService hallService;

    private Hall hall;
    private HallDto hallDto;
    @BeforeEach
    void setUp() {
        hall = Hall
                .builder()
                .name("test")
                .capacity(12)
                .sessions(List.of(Session.builder().startTime("12.00").build()))
                .seats(List.of(Seat.builder().seatNumber(12).build()))
                .build();
        hallDto = HallDto
                .builder()
                .capacity(12)
                .name("test")
                .sessions(List.of(SessionDto.builder().startTime("12.00").build()))
                .seats(List.of(SeatDto.builder().seatNumber(12).build()))
                .build();
    }

    @Test
    void save() throws Exception {
        when(hallService.save(any(HallDto.class))).thenReturn(hallDto);

        mockMvc.perform(post("/hall/save")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"test\",\"capacity\":12,\"sessions\":[{\"startTime\":\"12:00\"}]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

        verify(hallService,times(1)).save(any(HallDto.class));
    }

    @Test
    void getHalls() throws Exception {
        when(hallService.getHalls()).thenReturn(List.of(hallDto));

        mockMvc.perform(get("/hall/all")
                .contentType(APPLICATION_JSON)) // research about content  Type
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test"));

        verify(hallService,times(1)).getHalls();
    }

    @Test
    void updateHalls() throws Exception {
        long hallId = 1L;
        when(hallService.updateHalls(any(HallDto.class),anyLong())).thenReturn(hallDto);

        mockMvc.perform(put("/hall/update")
                        .param("id", String.valueOf(hallId))
                .contentType(APPLICATION_JSON)
                .content("{\"name\":\"test\",\"capacity\":12,\"sessions\":[{\"startTime\":\"12:00\"}]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));

        verify(hallService,times(1)).updateHalls(any(HallDto.class),anyLong());
    }

    @Test
    void deleteHall() throws Exception {
        long hallId = 1L;

        mockMvc.perform(delete("/hall/delete")
                .param("id",String.valueOf(hallId))
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(hallService, times(1)).deleteHall(anyLong());
    }
}