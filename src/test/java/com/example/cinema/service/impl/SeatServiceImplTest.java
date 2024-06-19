package com.example.cinema.service.impl;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.dto.TicketDto;
import com.example.cinema.entity.Seat;
import com.example.cinema.entity.Ticket;
import com.example.cinema.mapper.SeatMapper;
import com.example.cinema.repository.SeatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceImplTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private SeatMapper seatMapper;
    @Mock
    private SeatRepository seatRepository;
    @InjectMocks
    private SeatServiceImpl service;

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

    @AfterEach
    void tearDown() {
        seat = null;
        seatDto = null;
    }

    @Test // does not work !
    void save() {
        // Mocking behavior of seatMapper
        when(seatMapper.INSTANCE.seatDtoToSeat(seatDto)).thenReturn(seat);
        when(seatMapper.INSTANCE.seatToSeatDto(seat)).thenReturn(seatDto);

        // Mocking behavior of seatRepository
        when(seatRepository.save(seat)).thenReturn(seat);

        // When
        SeatDto savedSeatDto = service.save(seatDto);

        // Then
        // Verify that seatMapper's methods were called correctly
        verify(seatMapper, times(1)).INSTANCE.seatDtoToSeat(seatDto);
        verify(seatMapper, times(1)).INSTANCE.seatToSeatDto(seat);

        // Verify that seatRepository's save method was called exactly once with the correct seat object
        verify(seatRepository, times(1)).save(seat);

        // Assert the result
        assertThat(savedSeatDto).isNotNull();
        assertThat(savedSeatDto).isEqualTo(seatDto);
    }

    @Test
    void getSeats() {
        when(seatRepository.findAll()).thenReturn(List.of(seat));
        when(modelMapper.map(seat,SeatDto.class)).thenReturn(seatDto);
        List<SeatDto>seatDtoList = service.getSeats();
        verify(seatRepository,times(1)).findAll();
        verify(modelMapper,times(1)).map(seat,SeatDto.class);
    }

    @Test
    void updateSeats() {
        long seatId = 1l;
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));
        when(modelMapper.map(seat,Seat.class)).thenReturn(seat);
        doNothing().when(modelMapper).map(seatDto,seat);
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);
        SeatDto seatDtoRes = service.updateSeats(seatDto,seatId);
        verify(seatRepository,times(1)).findById(seatId);
        verify(modelMapper, times(1)).map(seat,Seat.class);
        verify(modelMapper,times(1)).map(seatDto,seat);
        verify(seatRepository,times(1)).save(seat);

        assertNotNull(seatDtoRes);
        assertEquals(seatDto.getSeatNumber(),seatDtoRes.getSeatNumber());
    }

    @Test
    void deleteSeat() {
        long seatId = 1l;
        when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));
        service.deleteSeat(seatId);
        verify(seatRepository,times(1)).findById(seatId);
        verify(seatRepository,times(1)).delete(seat);
    }
}