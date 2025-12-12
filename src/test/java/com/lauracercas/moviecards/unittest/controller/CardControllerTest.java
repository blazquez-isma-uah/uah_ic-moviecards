package com.lauracercas.moviecards.unittest.controller;

import com.lauracercas.moviecards.controller.CardController;
import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Card;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.actor.ActorService;
import com.lauracercas.moviecards.service.card.CardService;
import com.lauracercas.moviecards.service.movie.MovieService;
import com.lauracercas.moviecards.util.Messages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
class CardControllerTest {

    private CardController controller;
    private AutoCloseable closeable;

    @Mock
    MovieService movieServiceMock;
    @Mock
    ActorService actorServiceMock;
    @Mock
    CardService cardServiceMock;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
        controller = new CardController(movieServiceMock, actorServiceMock, cardServiceMock);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldInitializeCardPage() {
        List<Movie> movies = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();

        when(movieServiceMock.getAllMovies()).thenReturn(movies);
        when(actorServiceMock.getAllActors()).thenReturn(actors);

        String viewName = controller.showInfoForm(model);

        assertEquals("cards/registerActorMovieForm", viewName);
        verify(model).addAttribute("actors", actors);
        verify(model).addAttribute("movies", movies);
        verify(model).addAttribute("card", new Card());

    }

    @Test
    public void shouldRegisterCardSucess() {
        Card card = new Card();
        when(cardServiceMock.registerActorInMovie(card)).thenReturn(Messages.CARD_REGISTRATION_SUCCESS);

        String result = controller.registerCard(card, model);

        assertEquals("index", result);
        verify(model).addAttribute("message", Messages.CARD_REGISTRATION_SUCCESS);
    }

    @Test
    public void shouldRegisterCardFailure() {
        List<Movie> movies = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        Card card = new Card();
        when(cardServiceMock.registerActorInMovie(card)).thenReturn(Messages.CARD_ALREADY_EXISTS);

        String result = controller.registerCard(card, model);

        assertEquals("cards/registerActorMovieForm", result);
        verify(model).addAttribute("message", Messages.CARD_ALREADY_EXISTS);
        verify(model).addAttribute("actors", actors);
        verify(model).addAttribute("movies", movies);
        verify(model).addAttribute("card", new Card());
    }


}