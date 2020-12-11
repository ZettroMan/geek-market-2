package com.gb.zettro.market;

import com.gb.zettro.market.entities.Product;
import com.gb.zettro.market.services.ProductService;
import com.gb.zettro.market.utils.Cart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = Cart.class)
public class CartTest {
    @Autowired
    private Cart cart;

    @MockBean
    private ProductService productService;

    @Test
    public void cartFillingTest() {

        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Bread");
        product1.setPrice(10);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Butter");
        product2.setPrice(50);

        given(productService.findById(1L)).willReturn(Optional.of(product1));
        given(productService.findById(2L)).willReturn(Optional.of(product2));


        for (int i = 0; i < 5; i++) {
            cart.addOrIncrement(1L);
        }
        for (int i = 0; i < 2; i++) {
            cart.addOrIncrement(2L);
        }

        Assertions.assertAll(() -> {
                    Assertions.assertEquals(2, cart.getItems().size());
                },
                () -> {
                    Assertions.assertEquals(150, cart.getPrice());
                });
        cart.decrementOrRemove(2L);
        Assertions.assertEquals(100, cart.getPrice());
    }
}
