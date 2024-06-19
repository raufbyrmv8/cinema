package com.example.cinema.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieDto {
    String title;
    String description;
    String genre;
    int duration;
    List<SessionDto>sessions;
}
