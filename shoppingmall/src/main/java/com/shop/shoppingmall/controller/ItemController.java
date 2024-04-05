package com.shop.shoppingmall.controller;

import com.shop.shoppingmall.controller.dto.ItemDetailDto;
import com.shop.shoppingmall.domain.entity.CartEntity;
import com.shop.shoppingmall.domain.entity.Item;
import com.shop.shoppingmall.service.CartService;
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
    private final CartService cartService;
    private HttpSession session;

    @Autowired
    public ItemController(ItemService itemService, CartService cartService) {
        this.itemService = itemService;
        this.cartService = cartService;
    }

    @GetMapping
    public String mainView(Model model, HttpServletRequest request) {
        // 로그인 시 admin 권한 확인
        session = request.getSession();

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
    public String detail(Model model, @PathVariable Long id, HttpServletRequest request) {
        session = request.getSession();

        ItemDetailDto item = itemService.showItem(id);
        model.addAttribute("item", item);
        model.addAttribute("userName", session.getAttribute("userName"));
        return "detail";
    }

    @PostMapping("/item/cart/{id}")
    public String addCart(@PathVariable Long id) {
        String email = (String) session.getAttribute("userId");
        itemService.addCart(email, id);
        return "redirect:/item/{id}";
    }

    @GetMapping("/user/cart")
    public String getCart(Model model) {
        String email = (String) session.getAttribute("userId");
        List<CartEntity> item = cartService.viewCartList(email);

        model.addAttribute("item", item);
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("userName", session.getAttribute("userName"));

        return "item/cart";
    }
}
