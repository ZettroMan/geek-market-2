package com.gb.zettro.market.controllers;

import com.gb.zettro.market.dto.ProductCreateDto;
import com.gb.zettro.market.entities.Category;
import com.gb.zettro.market.entities.Product;
import com.gb.zettro.market.exceptions.MarketError;
import com.gb.zettro.market.services.CategoryService;
import com.gb.zettro.market.utils.ProductFilter;
import com.gb.zettro.market.dto.ProductDto;
import com.gb.zettro.market.exceptions.ResourceNotFoundException;
import com.gb.zettro.market.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping(produces = "application/json") // /api/v1/products
    public Page<ProductDto> getAllProducts(@RequestParam(defaultValue = "1", name = "p") Integer page,
                                           @RequestParam Map<String, String> params) {
        if (page < 1) {
            page = 1;
        }
        ProductFilter productFilter = new ProductFilter(params);
        Page<Product> content = productService.findAll(productFilter.getSpec(), page - 1, 5);
        Page<ProductDto> out = new PageImpl<>(content.getContent().stream().map(ProductDto::new).collect(Collectors.toList()), content.getPageable(), content.getTotalElements());
        return out;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find product with id: " + id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateDto p) {
        if(p.getCategoryId() == 0)
        {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Invalid category value"), HttpStatus.BAD_REQUEST);
        }
        Product newProduct = new Product();
        newProduct.setTitle(p.getTitle());
        newProduct.setPrice(p.getPrice());
        Category category = categoryService.findById(p.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Unable to find category with id: " + p.getCategoryId()));
        newProduct.setCategory(category);
        productService.saveOrUpdate(newProduct);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public Product updateProduct(@RequestBody Product p) {
        return productService.saveOrUpdate(p);
    }

    @DeleteMapping
    public void deleteAll() {
        productService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
