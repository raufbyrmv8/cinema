package com.example.cinema.repository;

import com.example.cinema.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query(value = "SELECT * FROM movie m where m.title=?1", nativeQuery = true)
    List<Movie>findByTitle(String title);

//    @EntityGraph(value = "Movie.sessions")
    Optional<Movie>findById(long id);
}
