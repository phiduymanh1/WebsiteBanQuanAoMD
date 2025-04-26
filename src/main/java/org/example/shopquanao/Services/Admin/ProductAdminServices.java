package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Entity.Product;
import org.example.shopquanao.Repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAdminServices {
    private final ProductsRepository productsRepository;

    public ProductAdminServices(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }
}
