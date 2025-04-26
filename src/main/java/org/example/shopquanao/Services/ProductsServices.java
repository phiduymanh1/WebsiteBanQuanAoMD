package org.example.shopquanao.Services;

import org.example.shopquanao.Entity.Product;
import org.example.shopquanao.Repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServices {
    private final ProductsRepository SanPhamRepository;

    public ProductsServices(ProductsRepository sanPhamRepository) {
        SanPhamRepository = sanPhamRepository;
    }

    public List<Product> getProducts() {
        return SanPhamRepository.findAll();
    }

    public Product detailProduct(int id) {
        return SanPhamRepository.findById(id).get();
    }

    public void addProduct(Product product) {
        SanPhamRepository.save(product);
    }

    public void removeProduct(Product product) {
        SanPhamRepository.delete(product);
    }


}
