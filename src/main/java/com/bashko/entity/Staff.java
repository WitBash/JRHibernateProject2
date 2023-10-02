package com.bashko.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    Byte id;

    @Column(name = "first_name", length = 45, nullable = false)
    String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    String lastName;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    Address address;

    @Lob
    @Column(name = "picture", columnDefinition = "BLOB")
    @ToString.Exclude
    Byte[] picture;

    @Column(name = "email", length = 50)
    String email;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @ToString.Exclude
    Store store;

    @Column(name = "active", nullable = false, columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    Boolean active;

    @Column(name = "username", length = 16, nullable = false)
    String username;

    @Column(name = "password", length = 40)
    String password;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    Timestamp lastUpdate;

    @OneToMany(mappedBy = "staff")
    @ToString.Exclude
    Set<Rental> rental;

    @OneToMany(mappedBy = "staff")
    @ToString.Exclude
    Set<Payment> payment;
}
