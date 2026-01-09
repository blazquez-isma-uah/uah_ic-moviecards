package com.lauracercas.moviecards.integrationtest;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.service.actor.ActorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Test de integración para ActorService
 * Prueba la integración del servicio con RestTemplate mockeado
 */
@SpringBootTest
public class ActorServiceIT {

    @Autowired
    private ActorService actorService;

    @MockBean
    private RestTemplate template;

    private static final String BASE_URL = "https://moviecards-service-blazquez.azurewebsites.net/actors";

    @Test
    public void testGetAllActors() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1975-06-09");
        Date deadDate = dateFormat.parse("2023-01-15");

        Actor actor1 = new Actor();
        actor1.setId(1);
        actor1.setName("Actor 1");
        actor1.setBirthDate(birthDate);
        actor1.setCountry("Spain");
        actor1.setDeadDate(null);

        Actor actor2 = new Actor();
        actor2.setId(2);
        actor2.setName("Actor 2");
        actor2.setBirthDate(birthDate);
        actor2.setCountry("USA");
        actor2.setDeadDate(deadDate);
        Actor[] actors = {actor1, actor2};

        when(template.getForObject(eq(BASE_URL), eq(Actor[].class)))
                .thenReturn(actors);

        List<Actor> result = actorService.getAllActors();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Actor 1", result.get(0).getName());
        assertEquals("Spain", result.get(0).getCountry());
        assertNull(result.get(0).getDeadDate());
        
        assertEquals("Actor 2", result.get(1).getName());
        assertEquals("USA", result.get(1).getCountry());
        assertNotNull(result.get(1).getDeadDate());
        assertEquals(deadDate, result.get(1).getDeadDate());
    }

    @Test
    public void testGetActorByIdWithDeadDate() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1980-03-20");
        Date deadDate = dateFormat.parse("2020-08-28");

        Actor actor = new Actor();
        actor.setId(1);
        actor.setName("Test Actor");
        actor.setBirthDate(birthDate);
        actor.setCountry("France");
        actor.setDeadDate(deadDate);

        when(template.getForObject(eq(BASE_URL + "/1"), eq(Actor.class)))
                .thenReturn(actor);

        Actor result = actorService.getActorById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Actor", result.getName());
        assertEquals("France", result.getCountry());
        assertNotNull(result.getDeadDate());
        assertEquals(deadDate, result.getDeadDate());
    }

    @Test
    public void testGetActorByIdWithoutDeadDate() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1990-05-10");

        Actor actor = new Actor();
        actor.setId(2);
        actor.setName("Alive Actor");
        actor.setBirthDate(birthDate);
        actor.setCountry("UK");
        actor.setDeadDate(null);

        when(template.getForObject(eq(BASE_URL + "/2"), eq(Actor.class)))
                .thenReturn(actor);

        Actor result = actorService.getActorById(2);

        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("Alive Actor", result.getName());
        assertNull(result.getDeadDate());
    }

    @Test
    public void testSaveNewActor() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1985-12-25");

        Actor newActor = new Actor();
        newActor.setId(0);
        newActor.setName("New Actor");
        newActor.setBirthDate(birthDate);
        newActor.setCountry("Italy");
        newActor.setDeadDate(null);

        when(template.postForObject(eq(BASE_URL), any(Actor.class), eq(String.class)))
                .thenReturn("1");

        Actor result = actorService.save(newActor);

        assertNotNull(result);
        assertEquals("New Actor", result.getName());
        assertEquals("Italy", result.getCountry());
        assertNotNull(result.getBirthDate());
        assertNull(result.getDeadDate());
    }

    @Test
    public void testUpdateExistingActor() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1970-07-15");
        Date deadDate = dateFormat.parse("2022-11-30");

        Actor existingActor = new Actor();
        existingActor.setId(5);
        existingActor.setName("Updated Actor");
        existingActor.setBirthDate(birthDate);
        existingActor.setCountry("Germany");
        existingActor.setDeadDate(deadDate);

        Actor result = actorService.save(existingActor);

        assertNotNull(result);
        assertEquals(5, result.getId());
        assertEquals("Updated Actor", result.getName());
        assertNotNull(result.getDeadDate());
    }

    @Test
    public void testGetActorByIdNotFound() {
        when(template.getForObject(eq(BASE_URL + "/999"), eq(Actor.class)))
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.NOT_FOUND));

        assertThrows(Exception.class, () -> {
            actorService.getActorById(999);
        });
    }

    @Test
    public void testGetAllActorsServerError() {
        when(template.getForObject(eq(BASE_URL), eq(Actor[].class)))
                .thenThrow(new HttpServerErrorException(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(Exception.class, () -> {
            actorService.getAllActors();
        });
    }

    @Test
    public void testSaveActorServerError() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = dateFormat.parse("1995-09-15");

        Actor newActor = new Actor();
        newActor.setId(0);
        newActor.setName("Error Actor");
        newActor.setBirthDate(birthDate);
        newActor.setCountry("Netherlands");
        newActor.setDeadDate(null);

        when(template.postForObject(eq(BASE_URL), any(Actor.class), eq(String.class)))
                .thenThrow(new HttpServerErrorException(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(Exception.class, () -> {
            actorService.save(newActor);
        });
    }
}
