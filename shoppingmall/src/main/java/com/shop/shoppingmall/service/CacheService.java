package com.shop.shoppingmall.service;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
        public String generateCacheKey(String email) {
        return "cart_list:" + email;
    }
}
