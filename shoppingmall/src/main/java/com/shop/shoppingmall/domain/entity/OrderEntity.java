package com.shop.shoppingmall.domain.entity;

import lombok.Getter;

@Getter
public class OrderEntity {
    private final Long id;
    private final String orderId;
    private final String orderPhone;
    private final int amount;

    public OrderEntity(Long id, String orderId, String orderPhone, int amount) {
        this.id = id;
        this.orderId = orderId;
        this.orderPhone = orderPhone;
        this.amount = amount;
    }
}
