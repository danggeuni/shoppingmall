package com.shop.shoppingmall.domain.entity;

import lombok.Getter;

@Getter
public class UserEntity {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;

    public UserEntity(String email, String password, String name, String phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
