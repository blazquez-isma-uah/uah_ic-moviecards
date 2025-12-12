package com.lauracercas.moviecards.service.actor;


import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.repositories.ActorJPA;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Service
public class ActorServiceImpl implements ActorService {

    private final ActorJPA actorJPA;

    public ActorServiceImpl(ActorJPA actorJPA) {
        this.actorJPA = actorJPA;
    }

    @Override
    public List<Actor> getAllActors() {
        return actorJPA.findAll();
    }

    @Override
    public Actor save(Actor actor) {
        return actorJPA.save(actor);
    }

    @Override
    public Actor getActorById(Integer actorId) {
        return actorJPA.getById(actorId);
    }
}
