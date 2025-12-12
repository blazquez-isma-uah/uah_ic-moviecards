package com.lauracercas.moviecards.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private String country;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;

    public Actor() {
    }

    public Actor(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id) && Objects.equals(name, actor.name) && Objects.equals(birthDate, actor.birthDate) && Objects.equals(country, actor.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, country);
    }
}