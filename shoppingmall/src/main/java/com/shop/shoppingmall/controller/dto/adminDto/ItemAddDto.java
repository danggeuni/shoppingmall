package com.shop.shoppingmall.controller.dto.adminDto;

import com.shop.shoppingmall.domain.entity.Item;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemAddDto {
    private final String name;
    private final Integer code;
    private final int price;
    private final int stock;
    private final String status;

    public ItemAddDto(String name, Integer code, Integer price, Integer stock, String status) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }

    public Item toEntity() {
        return new Item(null, code, name, price, stock, LocalDateTime.now(), LocalDateTime.now(), status);
    }
}
