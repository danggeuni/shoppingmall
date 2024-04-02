package com.shop.shoppingmall.controller;

import com.shop.shoppingmall.controller.dto.UserDto.UserJoinDto;
import com.shop.shoppingmall.controller.dto.UserDto.UserLoginDto;
import com.shop.shoppingmall.domain.entity.UserEntity;
import com.shop.shoppingmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
        session.setAttribute("userId", entity.getName());
        return "redirect:/";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("data", new UserJoinDto(null, null, null, null, null, null));
        return "/user/join";
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
}
