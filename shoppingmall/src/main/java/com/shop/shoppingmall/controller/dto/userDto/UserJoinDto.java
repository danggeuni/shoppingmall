package com.shop.shoppingmall.controller.dto.userDto;

import lombok.Getter;

@Getter
public class UserJoinDto {
    private final String email;
    private final String password;
    private final String confirmPwd;
    private final String name;
    private final String phone;
    private final String address;

    public UserJoinDto(String email, String password, String confirmPwd, String name, String phone, String address) {
        this.email = email;
        this.password = password;
        this.confirmPwd = confirmPwd;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
