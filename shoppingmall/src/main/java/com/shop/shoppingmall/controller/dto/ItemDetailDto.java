package com.shop.shoppingmall.controller.dto;

import lombok.Getter;

@Getter
public class ItemDetailDto {
    private final Long id;
    private final String name;
    private final int price;
    private final int stock;
    private final String img;

    public ItemDetailDto(Long id, String name, int price, int stock, String img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.img = img;
    }
}
