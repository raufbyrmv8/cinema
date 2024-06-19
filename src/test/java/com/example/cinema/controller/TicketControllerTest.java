package com.example.cinema.controller;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.dto.SessionDto;
import com.example.cinema.dto.TicketDto;
import com.example.cinema.dto.UserDto;
import com.example.cinema.entity.Seat;
import com.example.cinema.entity.Session;
import com.example.cinema.entity.Ticket;
import com.example.cinema.entity.User;
import com.example.cinema.service.TicketService;
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
class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TicketService ticketService;


    private Ticket ticket;
    private TicketDto ticketDto;
    private User user;
    private UserDto userDto;
    private Session session;
    private SessionDto sessionDto;
    private Seat seat;
    private SeatDto seatDto;
    @BeforeEach
    void setUp() {
        ticket = Ticket
                .builder()
                .price(12.5)
                .build();
        ticketDto = TicketDto
                .builder()
                .price(12.5)
                .build();

        user = User
                .builder()
                .balance(11.35)
                .build();

        session =Session
                .builder()
                .id(1l)
                .startTime("morning")
                .build();

        seat = Seat
                .builder()
                .seatNumber(11)
                .build();
        // Initialize DTOs if needed
        userDto = UserDto
                .builder()
                .balance(11.35)
                .build();
        sessionDto = SessionDto
                .builder()
                .startTime("morning")
                .build();
        seatDto = SeatDto
                .builder()
                .seatNumber(11)
                .build();
    }


    @Test
    void save() throws Exception {
        when(ticketService.save(any(TicketDto.class))).thenReturn(ticketDto);

        mockMvc.perform(post("/ticket/save")
                .contentType(APPLICATION_JSON)
                .content("{\"price\":12.5}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(12.5));
        verify(ticketService, times(1)).save(any(TicketDto.class));
    }

    @Test
    void getTickets() throws Exception {
        when(ticketService.getTickets()).thenReturn(List.of(ticketDto));

        mockMvc.perform(get("/ticket/all")
                .contentType(APPLICATION_JSON)
                .content("{\"price\":12.5}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").value(12.5));
        verify(ticketService, times(1)).getTickets();
    }

    @Test
    void updateTickets() throws Exception {
        long ticketId = 1L;
        when(ticketService.updateTickets(any(TicketDto.class),anyLong())).thenReturn(ticketDto);

        mockMvc.perform(put("/ticket/update")
                .param("id",String.valueOf(ticketId))
                .contentType(APPLICATION_JSON)
                .content("{\"price\":12.5}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(12.5));
        verify(ticketService, times(1)).updateTickets(any(),anyLong());
    }

    @Test
    void deleteTickets() throws Exception {
        long ticketId = 1L;

        mockMvc.perform(delete("/ticket/delete")
                .param("id",String.valueOf(ticketId))
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(ticketService,times(1)).deleteTickets(anyLong());
    }

    @Test
    void buyTicket() throws Exception {
        long sessionId = 1L;
        long userId = 1L;
        long seatId = 1L;
        when(ticketService.buyTicket(anyLong(),anyLong(),anyLong())).thenReturn(ticketDto);

        mockMvc.perform(post("/ticket/buy")
                .param("sessionId",String.valueOf(sessionId))
                .param("userId",String.valueOf(userId))
                .param("seatId",String.valueOf(seatId))
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(12.5));
        verify(ticketService,times(1)).buyTicket(anyLong(),anyLong(),anyLong());

    }

    @Test
    void returnTicket() throws Exception {
        long ticketId = 1L;
        long userId = 1L;

        mockMvc.perform(delete("/ticket/deleteTicket")
                .param("ticketId",String.valueOf(ticketId))
                .param("userId",String.valueOf(userId))
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(ticketService,times(1)).returnTicket(anyLong(),anyLong());
    }
}