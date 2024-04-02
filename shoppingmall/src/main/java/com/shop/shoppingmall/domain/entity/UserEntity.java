package com.shop.shoppingmall.domain.entity;

import lombok.Getter;

@Getter
public class UserEntity {
    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final String address;

    public UserEntity(String email, String password, String name, String phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
