package com.example.cinema.service.impl;

import com.example.cinema.dto.HallDto;
import com.example.cinema.dto.MovieDto;
import com.example.cinema.dto.SeatDto;
import com.example.cinema.dto.SessionDto;
import com.example.cinema.entity.Hall;
import com.example.cinema.entity.Movie;
import com.example.cinema.entity.Seat;
import com.example.cinema.entity.Session;
import com.example.cinema.mapper.HallMapper;
import com.example.cinema.repository.HallRepository;
import com.example.cinema.service.HallService;
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
class HallServiceImplTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private HallRepository hallRepository;
    @Mock
    private HallMapper hallMapper;
    @InjectMocks
    private HallServiceImpl hallService;
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

    @AfterEach
    void tearDown() {
        hall = null;
        hallDto = null;
    }

    @Test // does not work !
    void save() {
        when(hallMapper.INSTANCE.hallDtoToHall(any())).thenReturn(hall);
        when(hallMapper.INSTANCE.hallToHallDto(any())).thenReturn(hallDto);
        when(hallRepository.save(any())).thenReturn(hall);

        // When
        HallDto savedHallDto = hallService.save(hallDto);

        // Then
        assertNotNull(savedHallDto);
        assertEquals(hallDto.getName(), savedHallDto.getName());
        assertEquals(hallDto.getCapacity(), savedHallDto.getCapacity());

        verify(hallMapper, times(1)).INSTANCE.hallDtoToHall(any(HallDto.class));
        verify(hallMapper, times(1)).INSTANCE.hallToHallDto(any(Hall.class));

        verify(hallRepository, times(1)).save(any(Hall.class));
    }

    @Test
    void getHalls() {
        when(hallRepository.findAll()).thenReturn(List.of(hall));
        when(modelMapper.map(hall,HallDto.class)).thenReturn(hallDto);
        List<HallDto>hallDtoList = hallService.getHalls();
        verify(hallRepository,times(1)).findAll();

    }

    @Test
    void updateHalls() {
        long hallId = 1l;

        when(hallRepository.findById(hallId)).thenReturn(Optional.of(hall));
        when(modelMapper.map(hall,Hall.class)).thenReturn(hall);
        doNothing().when(modelMapper).map(hallDto,hall);
        when(hallRepository.save(any(Hall.class))).thenReturn(hall);
        HallDto hallDtoRes = hallService.updateHalls(hallDto,hallId);
        verify(hallRepository,times(1)).findById(hallId);
        verify(modelMapper,times(1)).map(hall,Hall.class);
        verify(modelMapper,times(1)).map(hallDto,hall);
        verify(hallRepository,times(1)).save(hall);

        assertNotNull(hallDtoRes);
        assertEquals(hallDto.getCapacity(), hallDtoRes.getCapacity());
    }

    @Test
    void deleteHall() {
        long hallId = 1l;
        when(hallRepository.findById(hallId)).thenReturn(Optional.of(hall));

        hallService.deleteHall(hallId);

        verify(hallRepository,times(1)).delete(hall);
    }
}