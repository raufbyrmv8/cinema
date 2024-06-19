package com.example.cinema.service.impl;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.dto.SessionDto;
import com.example.cinema.dto.TicketDto;
import com.example.cinema.dto.UserDto;
import com.example.cinema.entity.Seat;
import com.example.cinema.entity.Session;
import com.example.cinema.entity.Ticket;
import com.example.cinema.entity.User;
import com.example.cinema.mapper.TickerMapper;
import com.example.cinema.repository.SeatRepository;
import com.example.cinema.repository.SessionRepository;
import com.example.cinema.repository.TicketRepository;
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
class TicketServiceImplTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TickerMapper tickerMapper;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

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

    @AfterEach
    void tearDown() {
        ticket = null;
        ticketDto = null;
    }

    @Test
    void returnTicket() {
        long userId = 1L;
        long ticketId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        TicketDto result = ticketService.returnTicket(userId,ticketId);

        assertNotNull(result);
        assertEquals(12.5,result.getPrice());
        verify(userRepository,times(1)).findById(userId);
        verify(ticketRepository,times(1)).findById(ticketId);
        verify(userRepository, times(1)).save(any(User.class));
        verify(ticketRepository,times(1)).delete(any(Ticket.class));
    }

    @Test
    void buyTicket() {
        // Arrange
        long userId = 1L;
        long sessionId = 1L;
        long seatId = 1L;
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));
        // Act
        TicketDto result = ticketService.buyTicket(userId, sessionId, seatId);

        // Assert
        assertNotNull(result);
        assertEquals(11.35, result.getPrice());

        // Verify interactions
        verify(userRepository, times(1)).findById(userId);
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(seatRepository, times(1)).findById(seatId);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test // does not work!
    void save() {


        when(tickerMapper.INSTANCE.ticketDtoToTicket(ticketDto)).thenReturn(ticket);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(tickerMapper.INSTANCE.ticketToTicketDto(ticket)).thenReturn(ticketDto);

        // Act
        TicketDto savedTicketDto = ticketService.save(ticketDto);

        // Assert
        assertEquals(ticketDto, savedTicketDto);

        // Verify interactions
        verify(tickerMapper.INSTANCE).ticketDtoToTicket(ticketDto);
        verify(ticketRepository).save(ticket);
        verify(tickerMapper.INSTANCE).ticketToTicketDto(ticket);

    }

    @Test
    void getTickets() {
        when(ticketRepository.findAll()).thenReturn(List.of(ticket));
        when(modelMapper.map(ticket,TicketDto.class)).thenReturn(ticketDto);
        List<TicketDto>ticketDtoList = ticketService.getTickets();
        verify(ticketRepository, times(1)).findAll();
        verify(modelMapper,times(1)).map(ticket,TicketDto.class);
    }

    @Test
    void updateTickets() {
        long ticketId = 1l;
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(modelMapper.map(ticket,Ticket.class)).thenReturn(ticket);
        doNothing().when(modelMapper).map(ticketDto,ticket);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        TicketDto ticketDtoRes = ticketService.updateTickets(ticketDto,ticketId);
        verify(ticketRepository, times(1)).findById(ticketId);
        verify(modelMapper, times(1)).map(ticket,Ticket.class);
        verify(modelMapper,times(1)).map(ticketDto,ticket);
        verify(ticketRepository,times(1)).save(ticket);
        assertNotNull(ticketDtoRes);
        assertEquals(ticketDto.getPrice(), ticketDtoRes.getPrice());
    }

    @Test
    void deleteTickets() {
        long ticketId = 1l;
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        ticketService.deleteTickets(ticketId);
        verify(ticketRepository, times(1)).delete(ticket);
    }

}