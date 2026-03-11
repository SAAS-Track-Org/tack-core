package com.example.trackingcore.infrastructure.persistence.delivery;

import com.example.trackingcore.infrastructure.persistence.appuser.AppUserJpaEntity;
import com.example.trackingcore.infrastructure.persistence.deliveryman.DeliveryManEntity;
import com.example.trackingcore.infrastructure.persistence.order.OrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "delivery")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID publicCodeClient;

    @Column(nullable = false, unique = true)
    private UUID publicCodeDeliveryman;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUserJpaEntity appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryman_id", nullable = false)
    private DeliveryManEntity deliveryman;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private List<OrderEntity> orders = new ArrayList<>();

    private BigDecimal currentLat;

    private BigDecimal currentLng;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deliveredAt;
}
