package com.lauracercas.moviecards.service.actor;

import com.lauracercas.moviecards.model.Actor;

import java.util.List;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
public interface ActorService {

    List<Actor> getAllActors();

    Actor save(Actor actor);

    Actor getActorById(Integer actorId);
}
