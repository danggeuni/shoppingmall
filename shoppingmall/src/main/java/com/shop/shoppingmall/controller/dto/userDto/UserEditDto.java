package com.shop.shoppingmall.controller.dto.userDto;

import lombok.Getter;

@Getter
public class UserEditDto {
    private final String name;
    private final String password;
    private final String phone;
    private final String address;

    public UserEditDto(String name, String password, String phone, String address) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }
}
