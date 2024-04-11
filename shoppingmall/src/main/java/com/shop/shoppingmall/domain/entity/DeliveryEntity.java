package com.shop.shoppingmall.domain.entity;

import lombok.Getter;

@Getter
public class DeliveryEntity {
    private final Long id;
    private final Long orderNo;
    private final String orderId;
    private final String orderPhone;
    private final String delName;
    private final String delPhone;
    private final String delAddress;
    private final String memo;
    private final String status;

    public DeliveryEntity(Long id, Long orderNo, String orderId, String orderPhone, String delName, String delPhone, String delAddress, String memo, String status) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderId = orderId;
        this.orderPhone = orderPhone;
        this.delName = delName;
        this.delPhone = delPhone;
        this.delAddress = delAddress;
        this.memo = memo;
        this.status = status;
    }
}
