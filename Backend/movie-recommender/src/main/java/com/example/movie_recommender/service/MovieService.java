package com.example.movie_recommender.service;

import com.example.movie_recommender.model.Movie;
import com.example.movie_recommender.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    
    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${openai.api.key}")
    private String openAIAPIKey;
    
    @Autowired
    public MovieService(MovieRepository movieRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.movieRepository = movieRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }
    
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenresContaining(genre);
    }
    
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public String getRecommendationsFromPrompt(String userPrompt) {
        System.out.println("Received user prompt: " + userPrompt);

        // Construct the request payload (using OpenAI's Chat API for example)
        String jsonPayload = String.format("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}]}", userPrompt);
        System.out.println("Payload: " + jsonPayload); 

        // Set the API endpoint URL (OpenAI API URL)
        String url = "https://api.openai.com/v1/chat/completions";

        // Set up the HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAIAPIKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set up the HTTP request with body and headers
        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

        // Make the request to OpenAI
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            // Parse the response body (assuming it's a JSON response)
            String responseBody = response.getBody();
            if (responseBody != null) {
                // Use ObjectMapper to parse the response JSON and extract movie recommendations
                System.out.println("Response Body: " + responseBody); 
                var responseJson = objectMapper.readTree(responseBody);
                String movieRecommendations = responseJson
                        .path("choices")
                        .get(0)
                        .path("message")
                        .asText();

                return movieRecommendations;
            } else {
                return "No recommendations received from OpenAI.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while contacting OpenAI: " + e.getMessage();
        }
    }
}
