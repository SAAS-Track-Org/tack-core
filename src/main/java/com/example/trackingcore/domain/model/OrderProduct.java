package com.example.trackingcore.domain.model;

import lombok.Getter;

@Getter
public class OrderProduct {
    private final Order order;
    private final Product product;

    public static OrderProduct create(final Order order, final Product product) {
        return new OrderProduct(order, product);
    }

    public static OrderProduct with(final Order order, final Product product) {
        return new OrderProduct(order, product);
    }

    private OrderProduct(final Order order, final Product product) {
        this.order = order;
        this.product = product;
        this.validate();
    }

    private void validate() {
        if (order == null) {
            throw new IllegalArgumentException("OrderProduct order cannot be null");
        }
        if (product == null) {
            throw new IllegalArgumentException("OrderProduct product cannot be null");
        }
    }
}
