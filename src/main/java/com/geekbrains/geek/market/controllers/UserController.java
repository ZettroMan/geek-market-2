package com.geekbrains.geek.market.controllers;

import com.geekbrains.geek.market.entities.Category;
import com.geekbrains.geek.market.entities.User;
import com.geekbrains.geek.market.entities.UserInfo;
import com.geekbrains.geek.market.services.CategoryService;
import com.geekbrains.geek.market.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(produces = "application/json")
    public UserInfo getUserInfo(Principal principal) {
        String username = principal.getName();
        User user =  userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return user.getUserInfo();
    }

    @PutMapping
    public void saveUserInfo(@RequestBody UserInfo userInfo, Principal principal) {
        // Изменения в базе не сохраняются, не могу понять почему?
        String username = principal.getName();
        User user =  userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        user.setUserInfo(userInfo);
        userService.saveOrUpdate(user);
    }

}
