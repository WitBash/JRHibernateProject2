package com.bashko.entity;

import com.bashko.entity.Address;
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
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    Integer id;

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
    Set<Inventory> inventories = new HashSet<>();

    @OneToMany(mappedBy = "store")
    Set<Customer> customers = new HashSet<>();
}
