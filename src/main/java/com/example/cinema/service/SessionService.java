package com.example.cinema.service;

import com.example.cinema.dto.SessionDto;

import java.util.List;

public interface SessionService {
    SessionDto save(SessionDto sessionDto);
    List<SessionDto> getSessions();
    SessionDto updateSessions(SessionDto sessionDto, long id);
    void deleteSessions(long id);
}
