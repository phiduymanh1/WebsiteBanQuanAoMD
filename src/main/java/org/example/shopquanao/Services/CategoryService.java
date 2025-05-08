package org.example.shopquanao.Services;

import org.example.shopquanao.Entity.Category;
import org.example.shopquanao.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category findById(int id) {
        return categoryRepository.findById(id).get();
    }

    public void remove(int id) {
        categoryRepository.deleteById(id);
    }
}
