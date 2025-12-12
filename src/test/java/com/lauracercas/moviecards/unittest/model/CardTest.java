package com.lauracercas.moviecards.unittest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.lauracercas.moviecards.model.Card;


public class CardTest {

    Card card = new Card();

    @Test
    void testSetGetIdActor() {
        Integer idExample = 1;
        card.setIdActor(idExample);
        assertEquals(idExample, card.getIdActor());
    }

    @Test
    void testSetGetIdMovie() {
        Integer idExample = 1;
        card.setIdMovie(idExample);
        assertEquals(idExample, card.getIdMovie());
    }

}
