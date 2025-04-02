package com.example.movie_recommender.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String overview;
    private String posterPath;
    private Double voteAverage;
    private String releaseYear;
    
    @ElementCollection
    private List<String> genres;
}