package com.gb.zettro.market.controllers;

import com.gb.zettro.market.dto.OrderDto;
import com.gb.zettro.market.entities.User;
import com.gb.zettro.market.entities.Order;
import com.gb.zettro.market.exceptions.ResourceNotFoundException;
import com.gb.zettro.market.services.OrderService;
import com.gb.zettro.market.services.UserService;
import com.gb.zettro.market.utils.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    private final Cart cart;

    @GetMapping
    public List<OrderDto> getAllOrders(Principal principal) {
        return orderService.findAllUserOrdersDtosByUsername(principal.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(Principal principal, @RequestParam String address) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("Unable to create order for user: " + principal.getName() + ". User doesn't exist"));
        Order order = new Order(user, cart, address);
        orderService.save(order);
        cart.clear();
    }
}
