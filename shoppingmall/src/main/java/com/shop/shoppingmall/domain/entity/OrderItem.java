package com.shop.shoppingmall.domain.entity;

import lombok.Getter;

@Getter
public class OrderItem {

    private final Long id;
    private final Long itemId;
    private final int price;
    private final int amount;
    private final int count;

    public OrderItem(Long id, Long itemId, int price, int amount, int count) {
        this.id = id;
        this.itemId = itemId;
        this.price = price;
        this.amount = amount;
        this.count = count;
    }
}
