package com.example.cinema.service.impl;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.entity.Seat;
import com.example.cinema.entity.Ticket;
import com.example.cinema.mapper.SeatMapper;
import com.example.cinema.repository.SeatRepository;
import com.example.cinema.service.SeatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final ModelMapper modelMapper;
    private final SeatMapper seatMapper;
    @Override
    public SeatDto save(SeatDto seatDto) {
        Seat seat = seatMapper.INSTANCE.seatDtoToSeat(seatDto);
        seat.getTickets()
                .forEach(ticket -> ticket.setSeat(seat));
        List<Ticket>tickets =seat.getTickets();
        seat.setTickets(tickets);
        return seatMapper.INSTANCE.seatToSeatDto(seatRepository.save(seat));
    }

    @Override
    public List<SeatDto> getSeats() {
        List<Seat>seats = seatRepository.findAll();
        return seats.stream()
                .map(seat -> modelMapper.map(seat,SeatDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SeatDto updateSeats(SeatDto seatDto, long id) {
        Seat seat = seatRepository.findById(id)
                .map(seatDto1 -> modelMapper.map(seatDto1,Seat.class))
                .orElseThrow(()-> new RuntimeException("Seat is not found this id"));
        modelMapper.map(seatDto,seat);
        return seatMapper.INSTANCE.seatToSeatDto(seatRepository.save(seat));
    }

    @Override
    public void deleteSeat(long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Seat is not found this id"));
        seatRepository.delete(seat);
    }
}
