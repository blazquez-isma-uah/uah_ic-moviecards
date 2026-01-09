package com.lauracercas.moviecards.service.movie;

import com.lauracercas.moviecards.model.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    RestTemplate template;
    String url = "https://moviecards-service-blazquez.azurewebsites.net/movies";

    @Override
    public List<Movie> getAllMovies() {
        Movie[] movies = template.getForObject(url, Movie[].class);
        List<Movie> moviesList = Arrays.asList(movies);
        return moviesList;
    }

    @Override
    public Movie save(Movie movie) {
        if (movie.getId() != null && movie.getId() > 0) {
            template.put(url, movie);
        } else {
            movie.setId(0);
            template.postForObject(url, movie, String.class);
        }
        return movie;
    }

    @Override
    public Movie getMovieById(Integer movieId) {
        Movie movie = template.getForObject(url + "/" + movieId, Movie.class);
        return movie;
    }
}
