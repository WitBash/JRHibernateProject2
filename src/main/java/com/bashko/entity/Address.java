package com.bashko.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    Short id;

    @Column(name = "address", length = 50, nullable = false)
    String address;

    @Column(name = "address2", length = 50)
    String address2;

    @Column(name = "district", nullable = false)
    String district;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    City city;

    @Column(name = "postal_code", length = 10)
    String postalCode;

    @Column(name = "phone", length = 20, nullable = false)
    String phone;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    Timestamp lastUpdate;
}
