package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Repository.ProductDetailRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductDetailServices {
    private final ProductDetailRepo productDetailRepo;
    public AdminProductDetailServices(ProductDetailRepo productDetailRepo) {
        this.productDetailRepo = productDetailRepo;
    }

    public List<ProductDetail> findAll() {
        return productDetailRepo.findAll();
    }

    public ProductDetail findById(int id) {
        return productDetailRepo.findById(id).get();
    }

    public void save(ProductDetail productDetail) {
        productDetailRepo.save(productDetail);
    }

    public void delete(int id) {
        productDetailRepo.deleteById(id);
    }
}
