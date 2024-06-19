package com.example.cinema.service.impl;

import com.example.cinema.dto.SessionDto;
import com.example.cinema.entity.Session;
import com.example.cinema.entity.Ticket;
import com.example.cinema.mapper.SessionMapper;
import com.example.cinema.repository.SessionRepository;
import com.example.cinema.service.SessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final ModelMapper modelMapper;
    @Override
    public SessionDto save(SessionDto sessionDto) {
        Session session = sessionMapper.INSTANCE.sessionDtoToSession(sessionDto);
        session.getTickets()
                .forEach(ticket -> ticket.setSession(session));
        List<Ticket>tickets = session.getTickets();
        session.setTickets(tickets);
        return sessionMapper.INSTANCE.sessionToSessionDto(sessionRepository.save(session));
    }

    @Override
    public List<SessionDto> getSessions() {
     List<Session>sessions = sessionRepository.findAll();
     return sessions.stream()
             .map(session -> modelMapper.map(session, SessionDto.class))
             .collect(Collectors.toList());
    }

    @Override
    public SessionDto updateSessions(SessionDto sessionDto, long id) {
        Session session = sessionRepository.findById(id)
                        .map(session1 -> modelMapper.map(session1,Session.class))
                .orElseThrow(() -> new RuntimeException("Session not found this id"));
        modelMapper.map(sessionDto,session);
        return sessionMapper.INSTANCE.sessionToSessionDto(sessionRepository.save(session));
    }

    @Override
    public void deleteSessions(long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with this id"));
        sessionRepository.delete(session);
    }
}
