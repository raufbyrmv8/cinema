package com.example.cinema.mapper;

import com.example.cinema.dto.MovieDto;
import com.example.cinema.dto.SeatDto;
import com.example.cinema.entity.Movie;
import com.example.cinema.entity.Seat;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring",unmappedTargetPolicy = IGNORE)
@Component
public interface SeatMapper {
    SeatMapper INSTANCE = Mappers.getMapper(SeatMapper.class);

    SeatDto seatToSeatDto(Seat seat);
    @InheritInverseConfiguration
    Seat seatDtoToSeat(SeatDto seatDto);
}
