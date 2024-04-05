package com.shop.shoppingmall.service;

import com.shop.shoppingmall.domain.entity.CartEntity;
import com.shop.shoppingmall.domain.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartEntity> viewCartList(String email) {
        return cartRepository.viewCart(email);
    }
}
