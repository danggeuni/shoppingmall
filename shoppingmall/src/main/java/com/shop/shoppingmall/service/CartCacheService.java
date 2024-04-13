package com.shop.shoppingmall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shoppingmall.domain.entity.CartEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CartCacheService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> opsForValue;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CartCacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.opsForValue = redisTemplate.opsForValue();
    }

    private String generateCacheKey(String email) {
        return "cart_list:" + email;
    }

    public List<CartEntity> getCartList(String email) throws JsonProcessingException {
        String cachedValue = opsForValue.get(generateCacheKey(email));
        if (cachedValue != null) {
            return objectMapper.readValue(cachedValue, new TypeReference<>() {
            });
        }
        return null;
    }

    public void setCartList(List<CartEntity> cartEntities, String email) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(cartEntities);
        opsForValue.set(generateCacheKey(email), jsonString, 1, TimeUnit.DAYS);
        log.info("cached! : {}", jsonString);
    }

    public void deleteAll(String email) {
        redisTemplate.delete(generateCacheKey(email));
    }

}
