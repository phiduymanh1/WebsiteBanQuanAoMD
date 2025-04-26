package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Repository.ProductDetailRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailAdminServices {
    private final ProductDetailRepo productDetailRepo;

    public ProductDetailAdminServices(ProductDetailRepo sanPhamDetailRepo) {
        productDetailRepo = sanPhamDetailRepo;
    }

    public List<ProductDetail> getAllProductDetails() {
        return productDetailRepo.findAll();
    }

    public ProductDetail getProductDetail(Integer id) {
        return productDetailRepo.findById(id).orElseThrow();
    }
}
