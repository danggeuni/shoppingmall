package com.shop.shoppingmall.domain.entity;

import lombok.Getter;

@Getter
public class CartEntity {
    private final Long id;
    private final String email;
    private final String name;
    private final int price;

    public CartEntity(Long id, String email, String name, int price) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.price = price;
    }
}
