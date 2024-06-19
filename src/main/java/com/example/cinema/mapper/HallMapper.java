package com.example.cinema.mapper;

import com.example.cinema.dto.HallDto;
import com.example.cinema.dto.MovieDto;
import com.example.cinema.entity.Hall;
import com.example.cinema.entity.Movie;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring",unmappedTargetPolicy = IGNORE)
@Component
public interface HallMapper {
    HallMapper INSTANCE = Mappers.getMapper(HallMapper.class);

    HallDto hallToHallDto(Hall hall);
    @InheritInverseConfiguration
    Hall hallDtoToHall(HallDto hallDto);
}
