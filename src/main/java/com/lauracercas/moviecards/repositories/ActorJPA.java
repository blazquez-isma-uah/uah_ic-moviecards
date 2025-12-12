package com.lauracercas.moviecards.repositories;

import com.lauracercas.moviecards.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Repository
public interface ActorJPA extends JpaRepository<Actor, Integer> {


}
