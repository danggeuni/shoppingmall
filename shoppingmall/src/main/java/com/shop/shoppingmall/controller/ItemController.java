package com.shop.shoppingmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    public ItemController(ItemService itemService, CartService cartService) {
        this.itemService = itemService;
        this.cartService = cartService;
    }

    @GetMapping
    public String mainView(Model model, HttpServletRequest request) {
        HttpSession session;
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
    public String detail(Model model, @PathVariable Long id, HttpSession session) {
        ItemDetailDto item = itemService.showItem(id);
        model.addAttribute("item", item);
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("userName", session.getAttribute("userName"));
        return "detail";
    }

    @PostMapping("/item/cart/{id}")
    public String addCart(@PathVariable Long id, HttpSession session, int amount) {
        String email = (String) session.getAttribute("userId");

        if (email != null) {
            itemService.addCart(email, id, amount);
            return "redirect:/item/{id}";
        } else {
            return "redirect:/user/login";
        }
    }

    @GetMapping("/user/cart")
    public String getCart(Model model, HttpSession session) throws JsonProcessingException {
        String email = (String) session.getAttribute("userId");
        List<CartEntity> item = cartService.viewCartList(email);

        int totalPrice = 0;
        int totalQty = 0;
        for (CartEntity cartEntity : item) {
            totalPrice = totalPrice + (cartEntity.getPrice() * cartEntity.getCount());
            totalQty = totalQty + cartEntity.getCount();
        }

        model.addAttribute("item", item);
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalQty", totalQty);

        return "item/cart";
    }

    @GetMapping("/item/order/{id}")
    public String orderItem(Model model) {
        return "order";
    }

    @PostMapping("/item/order/")
    public void orderItem() {

    }

}
