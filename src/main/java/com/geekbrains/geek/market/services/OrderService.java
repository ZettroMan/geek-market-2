package com.geekbrains.geek.market.services;

import com.geekbrains.geek.market.entities.Order;
import com.geekbrains.geek.market.entities.Product;
import com.geekbrains.geek.market.repositories.OrderRepository;
import com.geekbrains.geek.market.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrUpdate(Order o) {
        return orderRepository.save(o);
    }
}
