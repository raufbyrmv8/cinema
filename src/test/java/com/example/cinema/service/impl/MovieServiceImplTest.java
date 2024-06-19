package com.example.cinema.service.impl;

import com.example.cinema.dto.MovieDto;
import com.example.cinema.dto.SessionDto;
import com.example.cinema.entity.Movie;
import com.example.cinema.entity.Session;
import com.example.cinema.mapper.MovieMapper;
import com.example.cinema.repository.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private MovieMapper movieMapper;
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieServiceImpl movieService;
    private Movie movie;
    private MovieDto movieDto;
    @BeforeEach
    void setUp() {
        movie = Movie
                .builder()
                .title("test")
                .description("test")
                .genre("test")
                .duration(12)
                .sessions(List.of(Session.builder().id(1l).startTime("12:00").build()))
                .build();
        movieDto = MovieDto
                .builder()
                .title("test")
                .description("test")
                .genre("test")
                .duration(12)
                .sessions(List.of(SessionDto.builder().startTime("12:00").build()))
                .build();
    }

    @AfterEach
    void tearDown() {
        movie = null;
        movieDto = null;
    }
    @Test // does not work, suppose to look again !
    void save() {
        when(movieMapper.movieDtoToMovie(any(MovieDto.class))).thenReturn(movie);
        when(movieMapper.movieToMovieDto(any(Movie.class))).thenReturn(movieDto);

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        MovieDto savedMovieDto = movieService.save(movieDto);

        verify(movieMapper, times(1)).INSTANCE.movieDtoToMovie(any(MovieDto.class));
        verify(movieMapper, times(1)).INSTANCE.movieToMovieDto(any(Movie.class));

        verify(movieRepository, times(1)).save(any(Movie.class));
        assertNotNull(savedMovieDto);

        assertEquals("test", savedMovieDto.getTitle());
        assertEquals("test", savedMovieDto.getDescription());
        assertEquals("test", savedMovieDto.getGenre());
        assertEquals(12, savedMovieDto.getDuration());
    }
    @Test
    void updateMovie() {
        long movieId = 1L;

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        when(modelMapper.map(movie, Movie.class)).thenReturn(movie);

        doNothing().when(modelMapper).map(movieDto, movie);

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        MovieDto updatedMovieDto = movieService.updateMovie(movieDto, movieId);

        // Verify interactions
        verify(movieRepository, times(1)).findById(movieId);
        verify(modelMapper, times(1)).map(movie, Movie.class);
        verify(modelMapper, times(1)).map(movieDto, movie);
        verify(movieRepository, times(1)).save(movie);

        // Assert the result (you might need to use a custom mapper for this assert)
        assertNotNull(updatedMovieDto);
        assertEquals(movieDto.getTitle(), updatedMovieDto.getTitle());
        assertEquals(movieDto.getDescription(), updatedMovieDto.getDescription());
        assertEquals(movieDto.getGenre(), updatedMovieDto.getGenre());
        assertEquals(movieDto.getDuration(), updatedMovieDto.getDuration());
    }
    @Test
    void deleteMovie() {
        long movieId = 1L;

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        movieService.deleteMovie(movieId);

        verify(movieRepository, times(1)).delete(movie);
    }
    @Test
    void findById() {
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));
        when(modelMapper.map(movie,MovieDto.class)).thenReturn(movieDto);
        Optional<MovieDto> movieRes = movieService.findById(1l);
        assertThat(movieRes.get().getTitle()).isEqualTo("test");
        verify(movieRepository,times(1)).findById(anyLong());
    }



    @Test
    void searchMovies() {
        when(movieRepository.findByTitle(anyString())).thenReturn(List.of(movie));
        when(modelMapper.map(movie,MovieDto.class)).thenReturn(movieDto);
        List<MovieDto>movieDtos = movieService.searchMovies(anyString());
        verify(movieRepository,times(1)).findByTitle(anyString());
    }


}