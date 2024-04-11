package com.shop.shoppingmall.controller.dto.adminDto;

import com.shop.shoppingmall.domain.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemAddDto {
    private final String name;
    private final String itemCode;
    private final int price;
    private final int stock;
    private final String status;

    public ItemAddDto(String name, String itemCode, Integer price, Integer stock, String status) {
        this.name = name;
        this.itemCode = itemCode;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }

    public Item toEntity() {
        return new Item(null, itemCode, name, price, stock, LocalDateTime.now(), LocalDateTime.now(), status, null);
    }
}
