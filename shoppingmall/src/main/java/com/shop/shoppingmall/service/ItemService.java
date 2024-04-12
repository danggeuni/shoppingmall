package com.shop.shoppingmall.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.shoppingmall.controller.dto.adminDto.ItemAddDto;
import com.shop.shoppingmall.controller.dto.ItemDetailDto;
import com.shop.shoppingmall.controller.dto.adminDto.ItemEditDto;
import com.shop.shoppingmall.domain.entity.CartEntity;
import com.shop.shoppingmall.domain.entity.ImgFile;
import com.shop.shoppingmall.domain.entity.Item;
import com.shop.shoppingmall.domain.repository.CartRepository;
import com.shop.shoppingmall.domain.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
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

    public void itemAdd(ItemAddDto itemAddDto, MultipartFile imgFile) {
        itemRepository.addItem(itemAddDto.toEntity());

        if (!imgFile.isEmpty()) {
            try {
                String originName = imgFile.getOriginalFilename();
                String projectPath = System.getProperty("user.dir") + "/shoppingmall/src/main/resources/static/files/";

                String savedFileName = UUID.randomUUID() + "_" + originName;

                File saveFile = new File(projectPath, savedFileName);
                imgFile.transferTo(saveFile);

                Long lastId = itemRepository.getLastItem().getId();
                ImgFile file = new ImgFile(lastId, originName, "/files/" + savedFileName);
                itemRepository.addImg(file);
            } catch (IOException e) {
                throw new RuntimeException("업로드에 실패하였습니다.");
            }
        }
    }

    public List<Item> showItems() {
        return itemRepository.showItems();
    }

    public ItemDetailDto showItem(Long id) {
        Item item = itemRepository.findById(id);
        return new ItemDetailDto(item.getId(), item.getName(), item.getPrice(), item.getStock(), item.getImgPath());
    }

    public List<Item> manageItems() {
        return itemRepository.manageItems();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id);
    }

    public void editItem(Long id, ItemEditDto dto) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("itemCode", dto.getItemCode());
        param.put("name", dto.getName());
        param.put("price", dto.getPrice());
        param.put("stock", dto.getStock());
        param.put("status", dto.getStatus());

        itemRepository.editItem(param);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteItem(id);
    }

    public void addCart(String email, Long id, int amount) {
        Item item = itemRepository.findById(id);

        // 데이터 존재하는지 확인
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("itemId", id);
        CartEntity entity = cartRepository.findCart(map);

        if (entity != null) {
            entity.addCount(amount);
            cartRepository.updateCart(entity);
        } else {
            Map<String, Object> map2 = new HashMap<>();
            map2.put("email", email);
            map2.put("count", amount);
            map2.put("itemId", item.getId());

            cartRepository.addCart(map2);
        }
        redisTemplate.delete(generateCacheKey(email));
    }

    private String generateCacheKey(String email) {
        return "cart_list:" + email;
    }
}