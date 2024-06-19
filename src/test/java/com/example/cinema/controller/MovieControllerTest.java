package com.example.cinema.controller;

import com.example.cinema.dto.MovieDto;
import com.example.cinema.dto.SessionDto;
import com.example.cinema.entity.Movie;
import com.example.cinema.entity.Session;
import com.example.cinema.service.MovieService;
import com.example.cinema.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

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
    }

    @Test
    void save() throws Exception {
        when(movieService.save(any(MovieDto.class))).thenReturn(movieDto);

        mockMvc.perform(post("/movie/save")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"test\",\"description\":\"test\",\"genre\":\"test\",\"duration\":12,\"sessions\":[{\"startTime\":\"12:00\"}]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test"));

        verify(movieService, times(1)).save(any(MovieDto.class));
    }

    @Test
    void findById() throws Exception {
        long movieId = 1L;
        when(movieService.findById(anyLong())).thenReturn(Optional.ofNullable(movieDto));

        mockMvc.perform(get("/movie/get")
                        .param("id", String.valueOf(movieId))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test"));
        verify(movieService,times(1)).findById(anyLong());
    }

    @Test
    void deleteMovie() throws Exception {
        long movieId = 1L;

        mockMvc.perform(delete("/movie/delete")
                .param("id",String.valueOf(movieId))
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(movieService,times(1)).deleteMovie(anyLong());

    }

    @Test
    void update() throws Exception {
        long movieId = 1L;
        when(movieService.updateMovie(any(MovieDto.class), anyLong())).thenReturn(movieDto);

        mockMvc.perform(put("/movie/update")
                        .param("id", String.valueOf(movieId))
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"test\",\"description\":\"test\",\"genre\":\"test\",\"duration\":12,\"sessions\":[{\"startTime\":\"12:00\"}]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test"));

        verify(movieService, times(1)).updateMovie(any(MovieDto.class), anyLong());
    }

    @Test
    void searchMovies() throws Exception {
        when(movieService.searchMovies(any(String.class))).thenReturn(List.of(movieDto));

        mockMvc.perform(get("/movie/getTitle")
                        .param("title","test")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test"));

        verify(movieService,times(1)).searchMovies(any(String.class));
    }
}