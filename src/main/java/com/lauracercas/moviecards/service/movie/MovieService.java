package com.lauracercas.moviecards.service.movie;

import com.lauracercas.moviecards.model.Movie;

import java.util.List;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
public interface MovieService {

    List<Movie> getAllMovies();

    Movie save(Movie movie);


    Movie getMovieById(Integer movieId);
}
