package com.shop.shoppingmall.service;

import com.shop.shoppingmall.controller.dto.ItemAddDto;
import com.shop.shoppingmall.controller.dto.ItemDetailDto;
import com.shop.shoppingmall.controller.dto.ItemEditDto;
import com.shop.shoppingmall.domain.entity.Item;
import com.shop.shoppingmall.domain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void itemAdd(ItemAddDto itemAddDto) {
        // 동일한 이름의 제품이 있는가?
        // findByName 찾고 비교
        itemRepository.addItem(itemAddDto.toEntity());
    }

    public List<Item> showItems() {
        return itemRepository.showItems();
    }

    public ItemDetailDto showItem(Long id) {
        Item item = itemRepository.findById(id);
        return new ItemDetailDto(item.getName(), item.getPrice(), item.getStock(), null);
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
}
