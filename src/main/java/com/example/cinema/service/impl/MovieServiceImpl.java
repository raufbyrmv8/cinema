package com.example.cinema.service.impl;

import com.example.cinema.dto.MovieDto;
import com.example.cinema.entity.Movie;
import com.example.cinema.entity.Session;
import com.example.cinema.mapper.MovieMapper;
import com.example.cinema.repository.MovieRepository;
import com.example.cinema.service.MovieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;
    private final MovieMapper movieMapper;


    @Override
    public MovieDto save(MovieDto movieDto) {
        Movie movie = movieMapper.INSTANCE.movieDtoToMovie(movieDto);
        movie.getSessions()
                .forEach(session -> session.setMovie(movie));
        List<Session>sessions = movie.getSessions();
        movie.setSessions(sessions);
        return movieMapper.INSTANCE.movieToMovieDto(movieRepository.save(movie));
    }

    @Override
    public MovieDto updateMovie(MovieDto movieDto,long id) {
        Movie movie = movieRepository.findById(id)
                .map(movie1-> modelMapper.map(movie1, Movie.class))
                .orElseThrow(() -> new RuntimeException("Movie not found this id"));
        modelMapper.map(movieDto,movie);
        return movieMapper.INSTANCE.movieToMovieDto(movieRepository.save(movie));
    }
    @Override
    public void deleteMovie(long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Movie not found this id"));
        movieRepository.delete(movie);
    }


    @Override
    public List<MovieDto> searchMovies(String title) {
        List<Movie> movies = movieRepository.findByTitle(title);
        return movies.stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toList());
    }



    public MovieDto findById(long id) {
       return movieRepository.findById(id)
               .map(movie -> modelMapper.map(movie,MovieDto.class))
               .orElseThrow(()-> new RuntimeException("not found"));
    }
}
