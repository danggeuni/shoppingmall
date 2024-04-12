package com.shop.shoppingmall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shoppingmall.domain.entity.CartEntity;
import com.shop.shoppingmall.domain.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final ValueOperations<String, String> opsForValue;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public CartService(CartRepository cartRepository, RedisTemplate<String, String> redisTemplate) {
        this.cartRepository = cartRepository;
        this.opsForValue = redisTemplate.opsForValue();
        this.redisTemplate = redisTemplate;
    }

    public List<CartEntity> viewCartList(String email) throws JsonProcessingException {
        String cached = opsForValue.get(generateCacheKey(email));

        if (cached != null) {
            log.info("cache hit!! : {}", cached);
            List<CartEntity> cartEntities = objectMapper.readValue(cached, new TypeReference<>() {});
            return cartEntities;
        }

        log.info("cache miss!!");

        List<CartEntity> cartEntities = cartRepository.viewCart(email);
        String jsonString = objectMapper.writeValueAsString(cartEntities);
        opsForValue.set(generateCacheKey(email), jsonString, 1, TimeUnit.DAYS);

        log.info("cached! : {}", jsonString);

        return cartEntities;
    }

    private String generateCacheKey(String email) {
        return "cart_list:" + email;
    }

    public void deleteCartItem(Long id, String email) {
        cartRepository.deleteById(id);
        redisTemplate.delete(generateCacheKey(email));
    }
}