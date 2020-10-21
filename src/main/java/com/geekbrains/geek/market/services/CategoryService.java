package com.geekbrains.geek.market.services;

import com.geekbrains.geek.market.entities.Category;
import com.geekbrains.geek.market.entities.Product;
import com.geekbrains.geek.market.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // дополнительные методы (пока не используются)
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    public Category saveOrUpdate(Category category) {
        return categoryRepository.save(category);
    }


}
