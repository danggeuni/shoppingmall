package com.shop.shoppingmall.domain.entity;

import lombok.Getter;

@Getter
public class OrderEntity {
    private final Long id;
    private final String email;
    private final String orderPhone;
    private final int amount;

    public OrderEntity(Long id, String email, String orderPhone, int amount) {
        this.id = id;
        this.email = email;
        this.orderPhone = orderPhone;
        this.amount = amount;
    }
}
