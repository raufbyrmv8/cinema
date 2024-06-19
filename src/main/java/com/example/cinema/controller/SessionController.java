package com.example.cinema.controller;

import com.example.cinema.dto.SessionDto;
import com.example.cinema.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {
    private final SessionService sessionService;
    @PostMapping("/save")
    public SessionDto save(@RequestBody SessionDto sessionDto){
        return sessionService.save(sessionDto);
    }
    @GetMapping("/all")
    public List<SessionDto> getAll(){
        return sessionService.getSessions();
    }
    @PutMapping("/update")
    public SessionDto updateSessions(@RequestBody SessionDto sessionDto, @Param("id") long id){
        return sessionService.updateSessions(sessionDto,id);
    }
    @DeleteMapping("/delete")
    public void deleteSessions(@Param("id") long id){
        sessionService.deleteSessions(id);
    }
}
