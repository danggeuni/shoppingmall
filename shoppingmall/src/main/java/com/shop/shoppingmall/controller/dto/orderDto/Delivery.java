package com.shop.shoppingmall.controller.dto.orderDto;

import lombok.Getter;

@Getter
public class Delivery {
    private final String orderName;
    private final String orderPhone;
    private final String orderAddress;
    private final String orderRequest;

    public Delivery(String orderName, String orderPhone, String orderAddress, String orderRequest) {
        this.orderName = orderName;
        this.orderPhone = orderPhone;
        this.orderAddress = orderAddress;
        this.orderRequest = orderRequest;
    }
}
