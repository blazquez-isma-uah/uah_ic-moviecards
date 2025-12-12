package com.lauracercas.moviecards.unittest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;

public class ActorTest {

    Actor actor = new Actor();

    @Test
    void testSetGetId() {
        Integer idExample = 1;
        actor.setId(idExample);
        assertEquals(idExample, actor.getId());
    }

    @Test
    void testSetGetName() {
        String nameExample = "Sample name";
        actor.setName(nameExample);
        assertEquals(nameExample, actor.getName());
    }

    @Test
    void testSetGetBirthDate() {
        Date birthDateExample = new Date();
        actor.setBirthDate(birthDateExample);
        assertEquals(birthDateExample, actor.getBirthDate());
    }

    @Test
    void testSetGetCountry() {
        String countryExample = "Sample country";
        actor.setCountry(countryExample);
        assertEquals(countryExample, actor.getCountry());

    }

    @Test
    void testSetGetMovies() {
        List<Movie> moviesExample = new ArrayList<Movie>();
        actor.setMovies(moviesExample);
        assertEquals(moviesExample, actor.getMovies());
    }

}
