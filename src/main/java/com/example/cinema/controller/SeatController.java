package com.example.cinema.controller;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seat")
public class SeatController {
    private final SeatService service;
    @PostMapping("/save")
    public SeatDto save(@RequestBody SeatDto seatDto){
        return service.save(seatDto);
    }
    @GetMapping("/all")
    public List<SeatDto>getSeats(){
        return service.getSeats();
    }
    @PutMapping("/update")
    public SeatDto updateSeats(@RequestBody SeatDto seatDto, @Param("id") long id){
        return service.updateSeats(seatDto,id);
    }
    @DeleteMapping("/delete")
    public void deleteSeat(@Param("id") long id){
        service.deleteSeat(id);
    }
}
