package com.example.trackingcore.infrastructure.persistence.paymentmethod;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_method", schema = "delivery")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodJpaEntity {

    @Id
    @Column(length = 50)
    private String id;

    @Column(nullable = false, length = 100)
    private String label;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}


