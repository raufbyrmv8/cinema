package com.example.cinema.service;

import com.example.cinema.dto.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    MovieDto save(MovieDto movieDto);
    MovieDto updateMovie(MovieDto movieDto,long id);
    void deleteMovie(long id);
    List<MovieDto>searchMovies(String title);
    MovieDto findById(long id);
}
