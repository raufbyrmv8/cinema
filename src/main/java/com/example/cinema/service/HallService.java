package com.example.cinema.service;

import com.example.cinema.dto.HallDto;

import java.util.List;

public interface HallService {
    HallDto save(HallDto hallDto);
    List<HallDto>getHalls();
    HallDto updateHalls(HallDto hallDto, long id);
    void deleteHall(long id);
}
