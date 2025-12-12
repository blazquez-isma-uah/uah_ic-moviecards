package com.lauracercas.moviecards.integrationtest.repositories;

import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.repositories.MovieJPA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@DataJpaTest
public class MovieJPAIT {

    @Autowired
    private MovieJPA movieJPA;

    @Test
    public void testSaveMovie() {
        Movie movie = new Movie();
        movie.setTitle("Movie");
        movie.setCountry("country");
        movie.setReleaseYear(1995);
        movie.setDuration(190);
        movie.setDirector("Director");
        movie.setGenre("Genre");
        movie.setSinopsis("sinopsis");

        Movie savedMovie = movieJPA.save(movie);

        assertNotNull(savedMovie.getId());

        Optional<Movie> foundMovie = movieJPA.findById(savedMovie.getId());

        assertTrue(foundMovie.isPresent());
        assertEquals(savedMovie, foundMovie.get());
    }

    @Test
    public void testFindById() {
        Movie movie = new Movie();
        movie.setTitle("movie2");
        Movie savedMovie = movieJPA.save(movie);

        Optional<Movie> foundMovie = movieJPA.findById(savedMovie.getId());
        assertTrue(foundMovie.isPresent());
        assertEquals(savedMovie, foundMovie.get());
    }
}
