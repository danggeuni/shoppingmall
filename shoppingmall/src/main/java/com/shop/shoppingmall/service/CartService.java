package com.shop.shoppingmall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shop.shoppingmall.domain.entity.CartEntity;
import com.shop.shoppingmall.domain.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartCacheService cartCacheService;


    @Autowired
    public CartService(CartRepository cartRepository, CartCacheService cartCacheService) {
        this.cartRepository = cartRepository;
        this.cartCacheService = cartCacheService;
    }

    public List<CartEntity> viewCartList(String email) throws JsonProcessingException {
        List<CartEntity> cached = cartCacheService.getCartList(email);

        if (cached != null) {
            log.info("cache hit!! : {}", cached);
            return cached;
        }

        log.info("cache miss!!");

        List<CartEntity> cartEntities = cartRepository.viewCart(email);
        cartCacheService.setCartList(cartEntities, email);


        return cartEntities;
    }

    public void deleteCartItem(Long id, String email) {
        cartRepository.deleteById(id);
        cartCacheService.deleteAll(email);
    }
}