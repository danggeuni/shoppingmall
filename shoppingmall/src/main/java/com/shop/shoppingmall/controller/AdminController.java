package com.shop.shoppingmall.controller;

import com.shop.shoppingmall.controller.dto.ItemAddDto;
import com.shop.shoppingmall.controller.dto.ItemEditDto;
import com.shop.shoppingmall.domain.entity.Item;
import com.shop.shoppingmall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class AdminController {
    private final ItemService itemService;

    @Autowired
    public AdminController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/manage")
    public String manageItem(Model model) {
        List<Item> item = itemService.manageItems();
        model.addAttribute("item", item);
        return "manageItem";
    }

    @GetMapping("/manage/edit/{id}")
    public String editItem(Model model, @PathVariable Long id) {
        Item item = itemService.findById(id);
        model.addAttribute("item", new ItemEditDto(id, item.getCode(), item.getName(), item.getPrice(), item.getStock(), item.getStatus()));

        return "editItem";
    }

    @PostMapping("/manage/edit/{id}")
    public String editItem(@PathVariable Long id, @ModelAttribute ItemEditDto dto) {
        itemService.editItem(id, dto);
        return "redirect:/manage";
    }

    @GetMapping("/add")
    public String addItem(Model model) {
        model.addAttribute("item", new ItemAddDto("", null, 0, 0, null));
        return "addItem";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute ItemAddDto add) {
        itemService.itemAdd(add);
        return "redirect:/";
    }
}
