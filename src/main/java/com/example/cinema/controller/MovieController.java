package com.example.cinema.controller;

import com.example.cinema.dto.MovieDto;
import com.example.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/get")
    public MovieDto findById(@Param("id") long id){
        return movieService.findById(id);
    }
    @PostMapping("/save")
    public MovieDto save(@RequestBody MovieDto movieDto){
        return movieService.save(movieDto);
    }

    @DeleteMapping("/delete")
    public void deleteMovie(@Param("id") long id){
         movieService.deleteMovie(id);
    }
    @PutMapping("/update")
    public MovieDto update(@RequestBody MovieDto movieDto, @Param("id") long id){
        return movieService.updateMovie(movieDto,id);
    }

    @GetMapping("/getTitle")
    public List<MovieDto>searchMovies(@RequestParam String title){
        return movieService.searchMovies(title);
    }
}
