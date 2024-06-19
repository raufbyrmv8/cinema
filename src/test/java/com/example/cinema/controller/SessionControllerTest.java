package com.example.cinema.controller;

import com.example.cinema.dto.SessionDto;
import com.example.cinema.dto.TicketDto;
import com.example.cinema.entity.Session;
import com.example.cinema.entity.Ticket;
import com.example.cinema.service.SeatService;
import com.example.cinema.service.SessionService;
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
class SessionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SessionService sessionService;

    private Session session;
    private SessionDto sessionDto;

    @BeforeEach
    void setUp() {
        session = Session
                .builder()
                .startTime("12")
                .tickets(List.of(Ticket.builder().build()))
                .build();
        sessionDto = SessionDto
                .builder()
                .startTime("12")
                .tickets(List.of(TicketDto.builder().build()))
                .build();
    }

    @Test
    void save() throws Exception {
        when(sessionService.save(any(SessionDto.class))).thenReturn(sessionDto);

        mockMvc.perform(post("/session/save")
                .contentType(APPLICATION_JSON)
                .content("{\"starTime\":12}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime").value(12));
        verify(sessionService,times(1)).save(any(SessionDto.class));
    }

    @Test
    void getAll() throws Exception {
        when(sessionService.getSessions()).thenReturn(List.of(sessionDto));

        mockMvc.perform(get("/session/all")
                .contentType(APPLICATION_JSON)
                .content("{\"starTime\":12}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].startTime").value(12));
        verify(sessionService,times(1)).getSessions();
    }

    @Test
    void updateSessions() throws Exception {
        long sessionId = 1L;
        when(sessionService.updateSessions(any(SessionDto.class),anyLong())).thenReturn(sessionDto);

        mockMvc.perform(put("/session/update")
                .param("id",String.valueOf(sessionId))
                .contentType(APPLICATION_JSON)
                .content("{\"starTime\":12}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startTime").value(12));
        verify(sessionService,times(1)).updateSessions(any(SessionDto.class),anyLong());
    }

    @Test
    void deleteSessions() throws Exception {
        long sessionId = 1L;
        mockMvc.perform(delete("/session/delete")
                .param("id",String.valueOf(sessionId))
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(sessionService, times(1)).deleteSessions(anyLong());
    }
}