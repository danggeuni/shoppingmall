package com.shop.shoppingmall.controller.dto.adminDto;

import lombok.Getter;

@Getter
public class ItemEditDto {
    private final Long id;
    private final String code;
    private final String name;
    private final int price;
    private final int stock;
    private final String status;

    public ItemEditDto(Long id, String code, String name, int price, int stock, String status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }
}
