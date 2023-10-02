package com.bashko.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    Byte id;

    @OneToOne
    @JoinColumn(name = "address_id")
    Address address;

    @OneToOne(mappedBy = "store")
    @JoinColumn(name = "manager_staff_id")
    Staff staff;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    Timestamp lastUpdate;

    @OneToMany(mappedBy = "store")
    @ToString.Exclude
    Set<Inventory> inventories;

    @OneToMany(mappedBy = "store")
    @ToString.Exclude
    Set<Customer> customers;
}
