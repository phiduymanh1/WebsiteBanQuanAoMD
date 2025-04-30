package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Entity.Product;
import org.example.shopquanao.Repository.ProductsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductServices {
    private final ProductsRepository productsRepository;
    public AdminProductServices(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    public Page<Product> getAllProductPage(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }


    public Product getProductById(int id) {
        return productsRepository.findById(id).get();
    }

    public void addProduct(Product product) {
        productsRepository.save(product);
    }

    public void removeProduct(int id) {
        productsRepository.deleteById(id);
    }
}
