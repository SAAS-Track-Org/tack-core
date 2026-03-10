package com.example.trackingcore.infrastructure.persistence.paymentmethod;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodJpaRepository extends JpaRepository<PaymentMethodJpaEntity, String> {
}

