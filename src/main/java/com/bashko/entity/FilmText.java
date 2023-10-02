package com.bashko.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "film_text")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class FilmText {

    @Id
    @Column(name = "film_id")
    Short id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "film_id")
    @ToString.Exclude
    Film film;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "description", columnDefinition = "text")
    @Type(type = "text")
    String description;
}
