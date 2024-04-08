package com.shop.shoppingmall.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shoppingmall.controller.dto.adminDto.ItemAddDto;
import com.shop.shoppingmall.controller.dto.ItemDetailDto;
import com.shop.shoppingmall.controller.dto.adminDto.ItemEditDto;
import com.shop.shoppingmall.domain.entity.Item;
import com.shop.shoppingmall.domain.repository.CartRepository;
import com.shop.shoppingmall.domain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public ItemService(ItemRepository itemRepository, CartRepository cartRepository, RedisTemplate<String, String> redisTemplate) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.redisTemplate = redisTemplate;
    }

    public void itemAdd(ItemAddDto itemAddDto) {
        itemRepository.addItem(itemAddDto.toEntity());
    }

    public List<Item> showItems() {
        return itemRepository.showItems();
    }

    public ItemDetailDto showItem(Long id) {
        Item item = itemRepository.findById(id);
        return new ItemDetailDto(item.getId(), item.getName(), item.getPrice(), item.getStock(), null);
    }

    public List<Item> manageItems() {
        return itemRepository.manageItems();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id);
    }

    public void editItem(Long id, ItemEditDto dto) {
        itemRepository.editItem(id, dto);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteItem(id);
    }

    public void addCart(String email, Long id) {
        Item item = itemRepository.findById(id);
        cartRepository.addCart(email, item);

        redisTemplate.delete(generateCacheKey(email));
    }

    private String generateCacheKey(String email) {
        return "cart_list:" + email;
    }
}
