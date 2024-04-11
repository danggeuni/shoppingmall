package com.shop.shoppingmall.controller;

import com.shop.shoppingmall.controller.dto.userDto.UserEditDto;
import com.shop.shoppingmall.controller.dto.userDto.UserJoinDto;
import com.shop.shoppingmall.controller.dto.userDto.UserLoginDto;
import com.shop.shoppingmall.domain.entity.CartEntity;
import com.shop.shoppingmall.domain.entity.UserEntity;
import com.shop.shoppingmall.service.CartService;
import com.shop.shoppingmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public UserController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("data", new UserLoginDto(null, null));
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDto userLoginDto, HttpServletRequest request) {
        UserEntity entity = userService.login(userLoginDto);

        HttpSession session = request.getSession();
        session.setAttribute("userName", entity.getName());
        session.setAttribute("userId", entity.getEmail());
        return "redirect:/";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("data", new UserJoinDto(null, null, null, null, null, null));
        return "user/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute UserJoinDto dto) {
        userService.joinUser(dto);
        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/info")
    public String userInfo(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("userId");
        UserEntity entity = userService.findById(id);

        model.addAttribute("data", entity);
        return "user/info";
    }

    @GetMapping("/edit")
    public String userEdit(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("userId");
        UserEntity entity = userService.findById(id);

        model.addAttribute("data", entity);
        model.addAttribute("edit", new UserEditDto(null, null, null, null));
        return "user/edit";
    }

    @PutMapping("/edit")
    public String userEdit(HttpServletRequest request, @ModelAttribute UserEditDto dto) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("userId");
        userService.editUser(dto, id);

        return "redirect:/user/info";
    }

    @DeleteMapping("cart/delete/{id}")
    public ResponseEntity<CartEntity> cartListDelete(@PathVariable Long id, HttpSession session) {
        String email = session.getAttribute("userId").toString();
        cartService.deleteCartItem(id, email);
        return  ResponseEntity.ok().build();
    }
}
