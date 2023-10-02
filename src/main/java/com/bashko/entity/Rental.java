package com.bashko.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "rental")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    Integer id;

    @Column(name = "rental_date", nullable = false)
    LocalDateTime rentalDate;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @Column(name = "return_date")
    LocalDateTime returnDate;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    Staff staff;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    Timestamp lastUpdate;

    @OneToOne(mappedBy = "rental")
    @ToString.Exclude
    Payment payment;
}
