package com.shop.shoppingmall.controller;

import com.shop.shoppingmall.controller.dto.ItemDetailDto;
import com.shop.shoppingmall.domain.entity.Item;
import com.shop.shoppingmall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionAttributes
@RequestMapping("/")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String mainView(Model model, HttpServletRequest request) {
        // 로그인 시 admin 권한 확인
        HttpSession session = request.getSession();

        if (session.getAttribute("userName") != null) {
            if (session.getAttribute("userId").toString().equals("admin@admin")) {
                model.addAttribute("admin", "admin");
                model.addAttribute("userId", session.getAttribute("userId"));
                model.addAttribute("userName", session.getAttribute("userName"));
            } else {
                model.addAttribute("userId", session.getAttribute("userId"));
                model.addAttribute("userName", session.getAttribute("userName"));
            }
        }

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
