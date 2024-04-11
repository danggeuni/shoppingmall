package com.shop.shoppingmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shop.shoppingmall.controller.dto.ItemDetailDto;
import com.shop.shoppingmall.controller.dto.adminDto.ItemEditDto;
import com.shop.shoppingmall.controller.dto.orderDto.Delivery;
import com.shop.shoppingmall.controller.dto.orderDto.SingleItem;
import com.shop.shoppingmall.domain.entity.CartEntity;
import com.shop.shoppingmall.domain.entity.Item;
import com.shop.shoppingmall.domain.entity.OrderEntity;
import com.shop.shoppingmall.domain.entity.UserEntity;
import com.shop.shoppingmall.service.CartService;
import com.shop.shoppingmall.service.ItemService;
import com.shop.shoppingmall.service.OrderService;
import com.shop.shoppingmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public ItemController(ItemService itemService, CartService cartService, OrderService orderService, UserService userService) {
        this.itemService = itemService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
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

    @GetMapping("/item/order")
    public String orderItem(Model model, HttpSession session, @RequestParam(required = false) Integer amount, @RequestParam(required = false) Long itemId) throws JsonProcessingException {
        String email = (String) session.getAttribute("userId");
        UserEntity entity = userService.findById(email);

        int totalPrice = 0;
        int totalQty = 0;

        if (amount != null) {
            Item itemInfo = itemService.findById(itemId);
            SingleItem item = new SingleItem(itemInfo.getId(), email, itemInfo.getId(), itemInfo.getName(), itemInfo.getPrice(), itemInfo.getStock(), amount);
            model.addAttribute("item", item);
            model.addAttribute("totalPrice", item.getPrice() * amount);
            model.addAttribute("totalQty", amount);
        } else {
            List<CartEntity> item = cartService.viewCartList(email);
            for (CartEntity cartEntity : item) {
                totalPrice = totalPrice + (cartEntity.getPrice() * cartEntity.getCount());
                totalQty = totalQty + cartEntity.getCount();
            }
            model.addAttribute("item", item);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalQty", totalQty);
        }

        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("user", entity);

        return "item/order";
    }

    @Transactional
    @PostMapping("/item/order/")
    public String orderItem(HttpSession session, Delivery delivery) throws JsonProcessingException {
        List<CartEntity> entity = cartService.viewCartList(session.getAttribute("userId").toString());
        UserEntity user = userService.findById(session.getAttribute("userId").toString());

        int totalPrice = 0;
        int totalQty = 0;

        for (CartEntity cartEntity : entity) {
            totalPrice = totalPrice + (cartEntity.getPrice() * cartEntity.getCount());
            totalQty = totalQty + cartEntity.getCount();
        }

        orderService.addOrder(user, totalPrice);
        OrderEntity order = orderService.findLastOrder();

        for (CartEntity e : entity) {
            if (e.getStock() == 0 || e.getStock() < e.getCount()) {
                return "redirect:/";
            }
            // 아이템 개별 추가
            orderService.addOrderItem(order.getId(), e, totalPrice);

            // 아이템 stock 수량 수정
            Item item = itemService.findById(e.getItemId());
            itemService.editItem(e.getItemId(), new ItemEditDto(item.getId(), item.getItemCode(), item.getName(), item.getPrice(), item.getStock() - e.getCount(), item.getStatus()));
            cartService.deleteCartItem(e.getId(), user.getEmail());
        }
        orderService.addDelivery(user, order.getId(), delivery);

        return "redirect:/user/cart";
    }
}
