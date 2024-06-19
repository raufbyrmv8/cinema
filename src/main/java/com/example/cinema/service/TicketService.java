package com.example.cinema.service;

import com.example.cinema.dto.TicketDto;

import java.util.List;

public interface TicketService {
    TicketDto save(TicketDto ticketDto);
    List<TicketDto>getTickets();
    TicketDto updateTickets(TicketDto ticketDto, long id);
    void deleteTickets(long id);

    TicketDto buyTicket(long userId, long sessionId, long seatId);
    TicketDto returnTicket(long userId, long ticketId);
}
