package com.shop.shoppingmall.domain.entity;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
public class Item {
    private final Long id;
    private final String itemCode;
    private final String name;
    private final int price;
    private final int stock;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;
    private final String status;
    private final String imgPath;

    public Item(Long id, String itemCode, String name, int price, int stock, LocalDateTime createAt, LocalDateTime updateAt, String status, String imgPath) {
        this.id = id;
        this.itemCode = itemCode;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
        this.imgPath = imgPath;
    }
}
