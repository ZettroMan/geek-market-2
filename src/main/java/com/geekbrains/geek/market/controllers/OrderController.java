package com.geekbrains.geek.market.controllers;

import com.geekbrains.geek.market.configs.JwtTokenUtil;
import com.geekbrains.geek.market.dto.JwtResponse;
import com.geekbrains.geek.market.entities.Order;
import com.geekbrains.geek.market.entities.Product;
import com.geekbrains.geek.market.entities.User;
import com.geekbrains.geek.market.exceptions.ResourceNotFoundException;
import com.geekbrains.geek.market.services.OrderService;
import com.geekbrains.geek.market.services.ProductService;
import com.geekbrains.geek.market.services.UserService;
import com.geekbrains.geek.market.utils.Cart;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final Cart cart;

    @GetMapping(produces = "application/json")
    public List<Order> showOrders() {
        return orderService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find order with id: " + id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createOrder(@RequestBody String address) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        Order newOrder = new Order(user, cart, address);
        newOrder.setId(null);
        orderService.saveOrUpdate(newOrder);
        return ResponseEntity.ok("Order created");
    }
}
