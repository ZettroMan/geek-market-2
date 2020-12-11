package com.gb.zettro.market;

import com.gb.zettro.market.entities.Category;
import com.gb.zettro.market.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class JsonTests {
    @Autowired
    private JacksonTester<Product> jackson;

    @Test
    public void jsonSerializationTest() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Food");
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Bread");
        product.setPrice(145);
        product.setCategory(category);
        // {
        //   "id": 1,
        //   "title": "Bread"
        //   "price": 145
        //   "category": { "id": 1L, "title": "Food" }
        // }
        assertThat(this.jackson.write(product)).hasJsonPathNumberValue("$.id");
        assertThat(this.jackson.write(product)).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(this.jackson.write(product)).extractingJsonPathStringValue("$.title").isEqualTo("Bread");
        assertThat(this.jackson.write(product)).extractingJsonPathNumberValue("$.price").isEqualTo(145);
        assertThat(this.jackson.write(product)).extractingJsonPathStringValue("$.category.title").isEqualTo("Food");
    }

    @Test
    public void jsonDeserializationTest() throws Exception {
        String content = "{\"id\": 10,\"title\":\"Samsung X100\", \"price\": 100500, \"category\": {\"id\": 3,\"title\":\"Notebook\"}}";
        Category category = new Category();
        category.setId(3L);
        category.setTitle("Notebook");
        Product realProduct = new Product();
        realProduct.setId(10L);
        realProduct.setTitle("Samsung X100");
        realProduct.setPrice(100500);
        realProduct.setCategory(category);

        assertThat(this.jackson.parse(content)).isEqualTo(realProduct);
        assertThat(this.jackson.parseObject(content).getCategory()).isEqualTo(category);
    }
}
