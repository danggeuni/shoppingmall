package com.shop.shoppingmall.controller.dto.orderDto;

import lombok.Getter;

@Getter
public class SingleItem {
    private final Long id;
    private final String email;
    private final Long itemId;
    private final String name;
    private final int price;
    private final int stock;
    private final int count;

    public SingleItem(Long id, String email, Long itemId, String name, int price, int stock, int count) {
        this.id = id;
        this.email = email;
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.count = count;
    }
}
