package com.example.trackingcore.infrastructure.persistence.deliveryman;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "deliveryman")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryManEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;
}
