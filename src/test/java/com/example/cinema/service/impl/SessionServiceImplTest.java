package com.example.cinema.service.impl;

import com.example.cinema.dto.SessionDto;
import com.example.cinema.dto.TicketDto;
import com.example.cinema.entity.Session;
import com.example.cinema.entity.Ticket;
import com.example.cinema.mapper.SessionMapper;
import com.example.cinema.repository.SessionRepository;
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
class SessionServiceImplTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private SessionMapper sessionMapper;
    @InjectMocks
    private SessionServiceImpl sessionService;
    @Mock
    private SessionRepository sessionRepository;
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

    @AfterEach
    void tearDown() {
        session = null;
        sessionDto = null;
    }

    @Test // does not work !
    void save() {
        // Mock behavior for sessionMapper
        when(sessionMapper.sessionDtoToSession(sessionDto)).thenReturn(session);

        // Mock behavior for sessionRepository
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        // Call the method to be tested
        SessionDto savedSessionDto = sessionService.save(sessionDto);

        // Assertions
        assertNotNull(savedSessionDto);
        assertEquals(session.getStartTime(), savedSessionDto.getStartTime());
        // You might need to adjust assertions based on what exactly you expect from the mapper

        // Verify interactions (optional)
        verify(sessionMapper, times(1)).sessionDtoToSession(sessionDto);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void getSessions() {
        when(sessionRepository.findAll()).thenReturn(List.of(session));
        when(modelMapper.map(session,SessionDto.class)).thenReturn(sessionDto);
        List<SessionDto>sessionDtoList = sessionService.getSessions();
        verify(sessionRepository,times(1)).findAll();
        verify(modelMapper, times(1)).map(session,SessionDto.class);
    }

    @Test
    void updateSessions() {
        long sessionId = 1l;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(modelMapper.map(session,Session.class)).thenReturn(session);
        doNothing().when(modelMapper).map(sessionDto,session);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        SessionDto sessionDtoRes = sessionService.updateSessions(sessionDto,sessionId);
        verify(sessionRepository,times(1)).findById(sessionId);
        verify(modelMapper,times(1)).map(session,Session.class);
        verify(modelMapper,times(1)).map(sessionDto,session);
        verify(sessionRepository,times(1)).save(session);
        assertNotNull(sessionDtoRes);
        assertEquals(sessionDto.getStartTime(),sessionDtoRes.getStartTime());
    }

    @Test
    void deleteSessions() {
        long sessionId = 1l;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        sessionService.deleteSessions(sessionId);
        verify(sessionRepository,times(1)).delete(session);
    }
}