package com.example.cinema.controller;

import com.example.cinema.dto.HallDto;
import com.example.cinema.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hall")
public class HallController {
    private final HallService hallService;
    @PostMapping("/save")
    public HallDto save(@RequestBody HallDto hallDto){
        return hallService.save(hallDto);
    }
    @GetMapping("/all")
    public List<HallDto>getHalls(){
        return hallService.getHalls();
    }
    @PutMapping("/update")
    public HallDto updateHalls(@RequestBody HallDto hallDto, @Param("id") long id){
        return hallService.updateHalls(hallDto,id);
    }
    @DeleteMapping("/delete")
    public void deleteHall(@Param("id") long id){
        hallService.deleteHall(id);
    }
}
