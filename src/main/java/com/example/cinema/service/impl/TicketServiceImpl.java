package com.example.cinema.service.impl;

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
import com.example.cinema.service.TicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;
    private final TickerMapper tickerMapper;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;


    @Override
    public TicketDto buyTicket(long userId, long sessionId, long seatId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found this id"));
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found id"));
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(()-> new RuntimeException("Seat is not found this id"));
        double price;
        if (session.getStartTime().equals("morning")) {
            price = 11.35;
        } else {
            price = 13.75;
        }
        if (user.getBalance()<price){
            throw new RuntimeException("Insufficient balance");
        }
        user.setBalance(user.getBalance() - price);
        userRepository.save(user);
        Ticket ticket = Ticket.builder()
                .price(price)
                .session(session)
                .seat(seat)
                .user(user)
                .build();

        ticketRepository.save(ticket);

        return new TicketDto(price);
    }


    @Override
    public TicketDto save(TicketDto ticketDto) {
        Ticket ticket = tickerMapper.INSTANCE.ticketDtoToTicket(ticketDto);
        return tickerMapper.INSTANCE.ticketToTicketDto(ticketRepository.save(ticket));
    }

    @Override
    public List<TicketDto> getTickets() {
        List<Ticket>tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket,TicketDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TicketDto updateTickets(TicketDto ticketDto, long id) {
        Ticket ticket = ticketRepository.findById(id)
                .map(ticket1 -> modelMapper.map(ticket1,Ticket.class))
                .orElseThrow(()-> new RuntimeException("Ticket is not this id"));
        modelMapper.map(ticketDto,ticket);
        return tickerMapper.INSTANCE.ticketToTicketDto(ticketRepository.save(ticket));
    }

    @Override
    public void deleteTickets(long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Ticket is not found this id"));
        ticketRepository.delete(ticket);

    }



    @Override
    public TicketDto returnTicket(long userId, long tickedId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found this id"));
        Ticket ticket = ticketRepository.findById(tickedId)
                .orElseThrow(()-> new RuntimeException("Ticket not found this id"));
        user.setBalance(user.getBalance() + ticket.getPrice());
        userRepository.save(user);

        ticketRepository.delete(ticket);
        return new TicketDto(ticket.getPrice());
    }
}
