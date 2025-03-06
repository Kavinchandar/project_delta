package com.example.movie_recommender.controller;

import com.example.movie_recommender.model.Movie;
import com.example.movie_recommender.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class MovieController {
    private final MovieService movieService;
    
    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    
    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        System.out.println("getAllMovies method is called.");
        return movieService.getAllMovies();
    }
    
    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/movies/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }

    @PostMapping("/recommendations")
    public ResponseEntity<String> getRecommendationsFromPrompt(@RequestBody Map<String, String> request) {

        // Extract the user prompt from the request body
        String userPrompt = request.get("prompt");
        if (userPrompt == null || userPrompt.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        // Call the service method to process the prompt and get recommendations
        String recommendations = movieService.getRecommendationsFromPrompt(userPrompt);

        return ResponseEntity.ok(recommendations);
    }
}