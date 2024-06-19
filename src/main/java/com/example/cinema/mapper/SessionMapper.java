package com.example.cinema.mapper;

import com.example.cinema.dto.SeatDto;
import com.example.cinema.dto.SessionDto;
import com.example.cinema.entity.Seat;
import com.example.cinema.entity.Session;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring",unmappedTargetPolicy = IGNORE)
@Component
public interface SessionMapper {
    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    SessionDto sessionToSessionDto(Session session);
    @InheritInverseConfiguration
    Session sessionDtoToSession(SessionDto sessionDto);
}
