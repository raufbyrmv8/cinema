package com.example.cinema.controller;

import com.example.cinema.dto.TicketDto;
import com.example.cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;
    @PostMapping("/save")
    public TicketDto save(@RequestBody TicketDto ticketDto){
        return ticketService.save(ticketDto);
    }
    @GetMapping("/all")
    public List<TicketDto>getTickets(){
        return ticketService.getTickets();
    }
    @PutMapping("/update")
    public TicketDto updateTickets(@RequestBody TicketDto ticketDto, @Param("id") long id){
        return ticketService.updateTickets(ticketDto,id);
    }
    @DeleteMapping("/delete")
    public void deleteTickets(@Param("id") long id){
        ticketService.deleteTickets(id);
    }

    @PostMapping("/buy")
    public TicketDto buyTicket(@RequestParam long userId, @RequestParam long sessionId, @RequestParam long seatId){
        return ticketService.buyTicket(userId,sessionId,seatId);
    }

    @DeleteMapping("/deleteTicket")
    public TicketDto returnTicket(@RequestParam long userId, @RequestParam long ticketId){
        return ticketService.returnTicket(userId,ticketId);
    }
}

