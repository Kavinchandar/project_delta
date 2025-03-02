package com.example.movie_recommender.repository;

import com.example.movie_recommender.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenresContaining(String genre);
    List<Movie> findByReleaseYear(Integer year);
    Optional<Movie> findByTitleContainingIgnoreCase(String title);
}