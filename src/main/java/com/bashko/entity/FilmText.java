package com.bashko.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "film_text")
public class FilmText {

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "film_id")
    Film film;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "description")
    String description;
}
