package com.example.cinema.controller;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.dto.TicketDto;
import com.example.cinema.entity.Seat;
import com.example.cinema.entity.Ticket;
import com.example.cinema.service.SeatService;
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
class SeatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SeatService seatService;
    private Seat seat;
    private SeatDto seatDto;
    @BeforeEach
    void setUp() {
        seat = Seat
                .builder()
                .seatNumber(12)
                .tickets(List.of(Ticket.builder().build()))
                .build();
        seatDto = SeatDto
                .builder()
                .seatNumber(12)
                .tickets(List.of(TicketDto.builder().build()))
                .build();
    }


    @Test
    void save() throws Exception {
        when(seatService.save(any(SeatDto.class))).thenReturn(seatDto);

        mockMvc.perform(post("/seat/save")
                .contentType(APPLICATION_JSON)
                .content("{\"seatNumber\":12}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seatNumber").value(12));

        verify(seatService,times(1)).save(any(SeatDto.class));
    }

    @Test
    void getSeats() throws Exception {
        when(seatService.getSeats()).thenReturn(List.of(seatDto));

        mockMvc.perform(get("/seat/all")
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].seatNumber").value(12));
        verify(seatService,times(1)).getSeats();
    }

    @Test
    void updateSeats() throws Exception {
        long seatId = 1L;
        when(seatService.updateSeats(any(SeatDto.class),anyLong())).thenReturn(seatDto);

        mockMvc.perform(put("/seat/update")
                .param("id",String.valueOf(seatId))
                .contentType(APPLICATION_JSON)
                .content("{\"seatNumber\":12}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seatNumber").value(12));
        verify(seatService,times(1)).updateSeats(any(SeatDto.class),anyLong());
    }

    @Test
    void deleteSeat() throws Exception {
        long seatId = 1L;

        mockMvc.perform(delete("/seat/delete")
                .param("id",String.valueOf(seatId))
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(seatService,times(1)).deleteSeat(anyLong());
    }
}