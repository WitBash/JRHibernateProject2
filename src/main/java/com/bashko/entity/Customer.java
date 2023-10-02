package com.bashko.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    Short id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @ToString.Exclude
    Store store;

    @Column(name = "first_name", length = 45, nullable = false)
    String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    String lastName;

    @Column(name = "email", length = 50)
    String email;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    Address address;

    @Column(name = "active", nullable = false, columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    Boolean active;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "last_update")
    Timestamp lastUpdate;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    Set<Rental> rentals;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    Set<Payment> payments;
}
