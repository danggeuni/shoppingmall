package com.shop.shoppingmall.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Item {
    private final Long id;
    private final Integer code;
    private final String name;
    private final int price;
    private final int stock;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;
    private final String status;

    public Item(Long id, Integer code, String name, int price, int stock, LocalDateTime createAt, LocalDateTime updateAt, String status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }
}
