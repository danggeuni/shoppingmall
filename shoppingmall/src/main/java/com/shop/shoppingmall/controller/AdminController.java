package com.shop.shoppingmall.controller;

import com.shop.shoppingmall.controller.dto.adminDto.ItemAddDto;
import com.shop.shoppingmall.controller.dto.adminDto.ItemEditDto;
import com.shop.shoppingmall.domain.entity.Item;
import com.shop.shoppingmall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage")
public class AdminController {
    private final ItemService itemService;

    @Autowired
    public AdminController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String manageItem(Model model, HttpSession session) {
        if(!session.getAttribute("userId").equals("admin@admin")) {
            throw new RuntimeException("권한이 없습니다.");
        }

        List<Item> item = itemService.manageItems();
        model.addAttribute("item", item);
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("userName", session.getAttribute("userName"));
        return "admin/manageItem";
    }

    @GetMapping("/edit/{id}")
    public String editItem(Model model, @PathVariable Long id, HttpSession session) {
        if(!session.getAttribute("userId").equals("admin@admin")) {
            throw new RuntimeException("권한이 없습니다.");
        }

        Item item = itemService.findById(id);
        model.addAttribute("item", new ItemEditDto(id, item.getItemCode(), item.getName(), item.getPrice(), item.getStock(), item.getStatus()));
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("userName", session.getAttribute("userName"));

        return "admin/editItem";
    }

    @PostMapping("/edit/{id}")
    public String editItem(@PathVariable Long id, @ModelAttribute ItemEditDto dto) {
        itemService.editItem(id, dto);
        return "redirect:/manage";
    }

    @GetMapping("/add")
    public String addItem(Model model, HttpSession session) {
        if(!session.getAttribute("userId").equals("admin@admin")) {
            throw new RuntimeException("권한이 없습니다.");
        }

        model.addAttribute("item", new ItemAddDto("", null, 0, 0, null));
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("userName", session.getAttribute("userName"));
        return "admin/addItem";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute ItemAddDto add, HttpSession session, MultipartFile imgFile) {
        if(!session.getAttribute("userId").equals("admin@admin")) {
            throw new RuntimeException("권한이 없습니다.");
        }

        itemService.itemAdd(add, imgFile);
        return "redirect:/manage";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id, HttpSession session) {
        if(!session.getAttribute("userId").equals("admin@admin")) {
            throw new RuntimeException("권한이 없습니다.");
        }

        itemService.deleteItem(id);
        return "redirect:/manage";
    }
}
