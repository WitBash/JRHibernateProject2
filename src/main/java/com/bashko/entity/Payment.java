package com.bashko.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    Staff staff;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rental_id")
    Rental rental;

    @Column(name = "amount", nullable = false)
    BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    LocalDateTime paymentDate;

    @UpdateTimestamp
    @Column(name = "last_update")
    Timestamp lastUpdate;
}
