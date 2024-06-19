package com.example.cinema.service.impl;

import com.example.cinema.dto.HallDto;
import com.example.cinema.entity.Hall;
import com.example.cinema.entity.Seat;
import com.example.cinema.entity.Session;
import com.example.cinema.mapper.HallMapper;
import com.example.cinema.repository.HallRepository;
import com.example.cinema.service.HallService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;
    private final ModelMapper modelMapper;
    private final HallMapper hallMapper;
    @Override
    public HallDto save(HallDto hallDto) {
        Hall hall = hallMapper.INSTANCE.hallDtoToHall(hallDto);
        hall.getSessions()
                .forEach(session -> session.setHall(hall));
        hall.getSeats()
                .forEach(seat -> seat.setHall(hall));
        List<Session>sessions = hall.getSessions();
        List<Seat>seats = hall.getSeats();
        hall.setSessions(sessions);
        hall.setSeats(seats);
        return hallMapper.INSTANCE.hallToHallDto(hallRepository.save(hall));
    }

    @Override
    public List<HallDto> getHalls() {
        List<Hall> halls = hallRepository.findAll();
        return halls.stream()
                .map(hall -> modelMapper.map(hall,HallDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public HallDto updateHalls(HallDto hallDto, long id) {
        Hall hall = hallRepository.findById(id)
                .map(hall1 -> modelMapper.map(hall1,Hall.class))
                .orElseThrow(()->new RuntimeException("Hall is not this id"));
        modelMapper.map(hallDto,hall);
        return hallMapper.INSTANCE.hallToHallDto(hallRepository.save(hall));
    }

    @Override
    public void deleteHall(long id) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Hall is not found this id"));
        hallRepository.delete(hall);
    }
}
