package com.shop.shoppingmall.controller.dto.userDto;

import lombok.Getter;

@Getter
public class UserLoginDto {
    private final String email;
    private final String password;

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
