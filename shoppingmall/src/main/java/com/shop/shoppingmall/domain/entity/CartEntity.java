package com.shop.shoppingmall.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class CartEntity {
    private Long id;
    private String email;
    private String name;
    private int price;

    public CartEntity() {
    }

    public CartEntity(Long id, String email, String name, int price) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.price = price;
    }
}
