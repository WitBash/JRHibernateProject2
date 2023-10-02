package com.bashko.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "language")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    Byte id;

    @Column(name = "name", length = 20, nullable = false, columnDefinition = "char")
    String name;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    Timestamp lastUpdate;

    @OneToMany(mappedBy = "language")
    @ToString.Exclude
    Set<Film> films;

    @OneToMany(mappedBy = "originalLanguage")
    @ToString.Exclude
    Set<Film> originalFilms;
}
