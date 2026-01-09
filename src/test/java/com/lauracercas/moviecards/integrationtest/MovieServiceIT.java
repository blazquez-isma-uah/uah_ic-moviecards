package com.lauracercas.moviecards.integrationtest;

import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.movie.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Test de integración para MovieService
 * Prueba la integración del servicio con RestTemplate mockeado
 */
@SpringBootTest
public class MovieServiceIT {

    @Autowired
    private MovieService movieService;

    @MockBean
    private RestTemplate template;

    private static final String BASE_URL = "https://moviecards-service-blazquez.azurewebsites.net/movies";


    @Test
    public void testGetAllMovies() throws Exception {
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setTitle("Movie 1");
        movie1.setDirector("Director 1");
        movie1.setCountry("Spain");
        movie1.setReleaseYear(2020);
        movie1.setDuration(120);
        movie1.setGenre("Action");
        movie1.setSinopsis("Synopsis 1");

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setTitle("Movie 2");
        movie2.setDirector("Director 2");
        movie2.setCountry("USA");
        movie2.setReleaseYear(2021);
        movie2.setDuration(150);
        movie2.setGenre("Drama");
        movie2.setSinopsis("Synopsis 2");

        Movie[] movies = {movie1, movie2};

        when(template.getForObject(eq(BASE_URL), eq(Movie[].class)))
                .thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Movie 1", result.get(0).getTitle());
        assertEquals("Director 1", result.get(0).getDirector());
        assertEquals("Spain", result.get(0).getCountry());
        assertEquals(2020, result.get(0).getReleaseYear());
        assertEquals("Movie 2", result.get(1).getTitle());
        assertEquals("Director 2", result.get(1).getDirector());
        assertEquals(150, result.get(1).getDuration());
        assertEquals("Drama", result.get(1).getGenre());
        assertEquals("Synopsis 2", result.get(1).getSinopsis());
    }

    @Test
    public void testGetMovieById() throws Exception {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setTitle("Test Movie");
        movie.setDirector("Test Director");
        movie.setCountry("France");
        movie.setReleaseYear(2022);
        movie.setDuration(135);
        movie.setGenre("Comedy");
        movie.setSinopsis("Test Synopsis");

        when(template.getForObject(eq(BASE_URL + "/1"), eq(Movie.class)))
                .thenReturn(movie);

        Movie result = movieService.getMovieById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Movie", result.getTitle());
        assertEquals("Test Director", result.getDirector());
        assertEquals("France", result.getCountry());
        assertEquals(2022, result.getReleaseYear());
        assertEquals(135, result.getDuration());
        assertEquals("Comedy", result.getGenre());
    }

    @Test
    public void testSaveNewMovie() throws Exception {
        Movie newMovie = new Movie();
        newMovie.setId(0);
        newMovie.setTitle("New Movie");
        newMovie.setDirector("New Director");
        newMovie.setCountry("Italy");
        newMovie.setReleaseYear(2023);
        newMovie.setDuration(100);
        newMovie.setGenre("Thriller");
        newMovie.setSinopsis("New Synopsis");

        when(template.postForObject(eq(BASE_URL), any(Movie.class), eq(String.class)))
                .thenReturn("1");

        Movie result = movieService.save(newMovie);

        assertNotNull(result);
        assertEquals("New Movie", result.getTitle());
        assertEquals("New Director", result.getDirector());
    }

    @Test
    public void testUpdateExistingMovie() throws Exception {
        Movie existingMovie = new Movie();
        existingMovie.setId(5);
        existingMovie.setTitle("Updated Movie");
        existingMovie.setDirector("Updated Director");
        existingMovie.setCountry("Germany");
        existingMovie.setReleaseYear(2019);
        existingMovie.setDuration(110);
        existingMovie.setGenre("Horror");
        existingMovie.setSinopsis("Updated Synopsis");

        Movie result = movieService.save(existingMovie);

        assertNotNull(result);
    }

    @Test
    public void testGetMovieByIdNotFound() {
        when(template.getForObject(eq(BASE_URL + "/999"), eq(Movie.class)))
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.NOT_FOUND));

        assertThrows(Exception.class, () -> {
            movieService.getMovieById(999);
        });
    }

    @Test
    public void testGetAllMoviesServerError() {
        when(template.getForObject(eq(BASE_URL), eq(Movie[].class)))
                .thenThrow(new HttpServerErrorException(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(Exception.class, () -> {
            movieService.getAllMovies();
        });
    }

    @Test
    public void testSaveMovieServerError() {
        Movie movie = new Movie();
        movie.setId(0);
        movie.setTitle("Error Movie");

        when(template.postForObject(eq(BASE_URL), any(Movie.class), eq(String.class)))
                .thenThrow(new HttpServerErrorException(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(Exception.class, () -> {
            movieService.save(movie);
        });
    }
}
