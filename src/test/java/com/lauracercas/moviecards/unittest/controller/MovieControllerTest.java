package com.lauracercas.moviecards.unittest.controller;

import com.lauracercas.moviecards.controller.MovieController;
import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.movie.MovieService;
import com.lauracercas.moviecards.util.Messages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
class MovieControllerTest {

    private MovieController controller;
    private AutoCloseable closeable;

    @Mock
    MovieService movieServiceMock;

    @Mock
    private Model model;


    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
        controller = new MovieController(movieServiceMock);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldGoListMovieAndGetAllMovies() {
        List<Movie> movies = new ArrayList<>();

        when(movieServiceMock.getAllMovies()).thenReturn(movies);

        String viewName = controller.getMoviesList(model);

        assertEquals("movies/list", viewName);
    }


    @Test
    public void shouldInitializeMovie() {
        String viewName = controller.newMovie(model);

        assertEquals("movies/form", viewName);

        verify(model).addAttribute("movie", new Movie());
        verify(model).addAttribute("title", Messages.NEW_MOVIE_TITLE);
    }

    @Test
    public void shouldSaveMovieWithNoErrors() {
        Movie movie = new Movie();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        when(movieServiceMock.save(any(Movie.class))).thenReturn(movie);

        String viewName = controller.saveMovie(movie, result, model);

        assertEquals("movies/form", viewName);

        verify(model).addAttribute("movie", movie);
        verify(model).addAttribute("title", Messages.EDIT_MOVIE_TITLE);
        verify(model).addAttribute("message", Messages.SAVED_MOVIE_SUCCESS);
    }

    @Test
    public void shouldUpdateMovieWithNoErrors() {
        Movie movie = new Movie();
        movie.setId(1);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        when(movieServiceMock.save(any(Movie.class))).thenReturn(movie);

        String viewName = controller.saveMovie(movie, result, model);

        assertEquals("movies/form", viewName);

        verify(model).addAttribute("movie", movie);
        verify(model).addAttribute("title", Messages.EDIT_MOVIE_TITLE);
        verify(model).addAttribute("message", Messages.UPDATED_MOVIE_SUCCESS);
    }


    @Test
    public void shouldTrySaveMovieWithErrors() {
        Movie movie = new Movie();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = controller.saveMovie(movie, result, model);

        assertEquals("movies/form", viewName);

        verifyNoInteractions(model);
    }


    @Test
    public void shouldGoToEditMovie() {
        Movie movie = new Movie();
        movie.setId(1);
        List<Actor> actors = List.of(new Actor());
        movie.setActors(actors);
        when(movieServiceMock.getMovieById(movie.getId())).thenReturn(movie);

        String viewName = controller.editMovie(movie.getId(), model);

        assertEquals("movies/form", viewName);

        verify(model).addAttribute("movie", movie);
        verify(model).addAttribute("actors", actors);
        verify(model).addAttribute("title", Messages.EDIT_MOVIE_TITLE);
    }

}