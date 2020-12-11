package com.gb.zettro.market.utils;

import com.gb.zettro.market.entities.OrderItem;
import com.gb.zettro.market.entities.Product;
import com.gb.zettro.market.exceptions.ResourceNotFoundException;
import com.gb.zettro.market.services.ProductService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@Slf4j
public class Cart {
    private final ProductService productService;
    private List<OrderItem> items;
    private int price;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
    }

    public void addOrIncrement(Long productId) {
//        System.out.println("Adding product with id = " + productId);
//        log.debug("Adding product with id = " + productId);
        for (OrderItem o : items) {
            if (o.getProduct().getId().equals(productId)) {
                o.incrementQuantity();
                recalculate();
                return;
            }
        }
        Product p = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Unable to find product with id: " + productId + " (add to cart)"));
//        System.out.println("Items length before = " + items.size());
//        log.debug("Items length before = " + items.size());
        items.add(new OrderItem(p));
//        System.out.println("Items length after = " + items.size());
//        log.debug("Items length after = " + items.size());
        recalculate();
    }

    public void decrementOrRemove(Long productId) {
        Iterator<OrderItem> iter = items.iterator();
        while (iter.hasNext()) {
            OrderItem o = iter.next();
            if (o.getProduct().getId().equals(productId)) {
                o.decrementQuantity();
                if (o.getQuantity() == 0) {
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void remove(Long productId) {
        Iterator<OrderItem> iter = items.iterator();
        while (iter.hasNext()) {
            OrderItem o = iter.next();
            if (o.getProduct().getId().equals(productId)) {
                iter.remove();
                recalculate();
                return;
            }
        }
    }

    public void recalculate() {
        price = 0;
        for (OrderItem o : items) {
            o.setPricePerProduct(o.getProduct().getPrice());
            price += o.getPrice();
        }
    }

    public void clear() {
        items.clear();
        price = 0;
    }
}
