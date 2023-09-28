package com.bashko.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    Integer id;

    @Column(name = "city", length = 50, nullable = false)
    String city;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    Country country;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    Timestamp lastUpdate;

    @OneToMany(mappedBy = "city")
    Set<Address> addresses = new HashSet<>();
}
