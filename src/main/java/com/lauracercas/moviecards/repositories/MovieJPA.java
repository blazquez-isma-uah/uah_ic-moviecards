package com.lauracercas.moviecards.repositories;

import com.lauracercas.moviecards.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
public interface MovieJPA extends JpaRepository<Movie, Integer> {


}
