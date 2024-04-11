package com.shop.shoppingmall.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class CartEntity {
    private Long id;
    private String email;
    private Long itemId;
    private String name;
    private int price;
    private int stock;
    private int count;

    public CartEntity() {
    }

    public CartEntity(Long id, String email, Long itemId) {
        this.id = id;
        this.email = email;
        this.itemId = itemId;
    }

    public CartEntity(long id, String email, long itemId, String name, int price, int stock, int count) {
        this.id = id;
        this.email = email;
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.count = count;
    }

    public void addCount(int amount){
        this.count = count + amount;
    }
}
