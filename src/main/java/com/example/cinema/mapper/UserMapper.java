package com.example.cinema.mapper;

import com.example.cinema.dto.SessionDto;
import com.example.cinema.dto.UserDto;
import com.example.cinema.entity.Session;
import com.example.cinema.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring",unmappedTargetPolicy = IGNORE)
@Component
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);
    @InheritInverseConfiguration
    User userDtoToUser(UserDto userDto);
}
