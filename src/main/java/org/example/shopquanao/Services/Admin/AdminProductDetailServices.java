package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Repository.ProductDetailRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ProductDetail> getProductDetail(Pageable pageable) {
        return productDetailRepo.findAll(pageable);
    }
    public Page<ProductDetail> searchProduct(String keyword, Pageable pageable) {
        return productDetailRepo.findByProductNameContainingIgnoreCase(keyword, pageable);
    }

    public List<ProductDetail> searchName(String keyword) {
        return productDetailRepo.findByProductNameContainingIgnoreCase1(keyword);
    }

}
