package com.geekbrains.geek.market.controllers;

import com.geekbrains.geek.market.entities.Category;
import com.geekbrains.geek.market.entities.Product;
import com.geekbrains.geek.market.exceptions.ResourceNotFoundException;
import com.geekbrains.geek.market.services.CategoryService;
import com.geekbrains.geek.market.services.ProductService;
import com.geekbrains.geek.market.utils.ProductFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class RestCategoriesController {

    CategoryService categoryService;

    @GetMapping(produces = "application/json") // /api/v1/categories
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }
}
