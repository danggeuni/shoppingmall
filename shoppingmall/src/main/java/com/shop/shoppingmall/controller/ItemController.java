package com.shop.shoppingmall.controller;

import com.shop.shoppingmall.controller.dto.ItemDetailDto;
import com.shop.shoppingmall.domain.entity.Item;
import com.shop.shoppingmall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String mainView(Model model) {
        List<Item> item = itemService.showItems();
        model.addAttribute("item", item);
        return "main";
    }

    @GetMapping("/item/{id}")
    public String detail(Model model, @PathVariable Long id) {
        ItemDetailDto item = itemService.showItem(id);
        model.addAttribute("item", item);
        return "detail";
    }
}
