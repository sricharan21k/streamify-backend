package com.app.streamify.service;

import com.app.streamify.model.Album;
import com.app.streamify.model.Movie;
import com.app.streamify.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie getMovie(Long movieId){
        return movieRepository.findById(movieId).orElseThrow();
    }

    public List<Movie> getNewReleases(){
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre){
        return movieRepository.getMoviesByGenre(genre);
    }

    public Map<String, List<Movie>> getMovieGenres() {
        return movieRepository.findAll().stream().sorted(Comparator.comparing(Movie::getGenre))
                .collect(Collectors.groupingBy(Movie::getGenre));
    }

}