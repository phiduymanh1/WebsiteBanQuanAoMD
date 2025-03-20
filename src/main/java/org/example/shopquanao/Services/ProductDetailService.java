package org.example.shopquanao.Services;

import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Repository.ProductDetailRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {
    private final ProductDetailRepo productDetailRepo;

    public ProductDetailService(ProductDetailRepo sanPhamDetailRepo) {
        productDetailRepo = sanPhamDetailRepo;
    }

    public List<ProductDetail> getAllProductDetails() {
        return productDetailRepo.findAll();
    }

    public ProductDetail getProductDetail(int id) {
        return productDetailRepo.findById(id).get();
    }
}
