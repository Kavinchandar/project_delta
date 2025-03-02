// src/main/java/com/example/movierecommender/config/DataLoader.java
package com.example.movie_recommender.config;

import com.example.movie_recommender.model.Movie;
import com.example.movie_recommender.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataLoader {
    
    @Bean
    public CommandLineRunner loadData(MovieRepository repository) {
        return args -> {
            // Add some sample movies
            Movie movie1 = new Movie();
            movie1.setTitle("The Shawshank Redemption");
            movie1.setOverview("Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.");
            movie1.setRating(9.3);
            movie1.setReleaseYear(1994);
            movie1.setGenres(Arrays.asList("Drama", "Crime"));
            
            Movie movie2 = new Movie();
            movie2.setTitle("The Godfather");
            movie2.setOverview("The aging patriarch of an organized crime dynasty transfers control to his reluctant son.");
            movie2.setRating(9.2);
            movie2.setReleaseYear(1972);
            movie2.setGenres(Arrays.asList("Crime", "Drama"));
            
            repository.saveAll(Arrays.asList(movie1, movie2));
        };
    }
}