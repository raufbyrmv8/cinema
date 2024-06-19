package com.example.cinema.service;

import com.example.cinema.dto.SeatDto;

import java.util.List;

public interface SeatService {
    SeatDto save(SeatDto seatDto);
    List<SeatDto>getSeats();
    SeatDto updateSeats(SeatDto seatDto, long id);
    void deleteSeat(long id);
}
