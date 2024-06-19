package com.example.cinema.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HallDto {
    String name;
    int capacity;
    List<SessionDto>sessions;
    List<SeatDto>seats;
}
