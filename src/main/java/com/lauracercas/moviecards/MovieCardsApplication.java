package com.lauracercas.moviecards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@SpringBootApplication
public class MovieCardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCardsApplication.class, args);
    }

    @Bean
    public RestTemplate template() {
        RestTemplate template = new RestTemplate();
        return template;
    }

}

