package com.example.cinema.mapper;

import com.example.cinema.dto.SessionDto;
import com.example.cinema.dto.TicketDto;
import com.example.cinema.entity.Session;
import com.example.cinema.entity.Ticket;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring",unmappedTargetPolicy = IGNORE)
@Component
public interface TickerMapper {
    TickerMapper INSTANCE = Mappers.getMapper(TickerMapper.class);

    TicketDto ticketToTicketDto(Ticket ticket);
    @InheritInverseConfiguration
    Ticket ticketDtoToTicket(TicketDto ticketDto);
}
