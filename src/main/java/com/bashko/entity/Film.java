package com.bashko.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    Integer id;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "release_year")
    Date releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    Language language;

    @ManyToOne
    @JoinColumn(name = "original_language_id")
    Language originalLanguage;

    @Column(name = "rental_duration", nullable = false)
    Integer rentalDuration;

    @Column(name = "rental_rate", nullable = false)
    BigDecimal rentalRate;

    @Column(name = "length")
    Integer length;

    @Column(name = "replacement_cost", nullable = false)
    BigDecimal replacementCost;

    @Column(name = "rating")
    String rating;

    @Column(name = "special_features")
    String specialFeatures;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    Timestamp lastUpdate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY, mappedBy = "films")
    Set<Actor> actors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "film_category",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "film")
    Set<Inventory> inventories = new HashSet<>();

    @OneToOne(mappedBy = "film")
    FilmText filmText;
}
