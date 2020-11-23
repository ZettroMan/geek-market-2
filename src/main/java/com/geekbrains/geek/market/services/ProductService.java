package com.geekbrains.geek.market.services;

import com.geekbrains.geek.market.entities.Product;
import com.geekbrains.geek.market.repositories.ProductRepository;
import com.geekbrains.geek.market.repositories.specifications.ProductSpecifications;
import com.geekbrains.geek.market.soap.ProductSOAP;
import com.geekbrains.geek.market.utils.ProductFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Function<Product, ProductSOAP> functionEntityToSoap = product -> {
        ProductSOAP productSOAP = new ProductSOAP();
        productSOAP.setId(product.getId());
        productSOAP.setPrice(product.getPrice());
        productSOAP.setTitle(product.getTitle());
        return productSOAP;
    };


    private final ProductRepository productRepository;

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }

    public Page<Product> findAll(Specification<Product> spec, int page, int size) {
        return productRepository.findAll(spec, PageRequest.of(page, size));
    }

    public List<ProductSOAP> findAll() {
        return productRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }
}
