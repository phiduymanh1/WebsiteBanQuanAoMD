package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Entity.ProductDetail;

import org.example.shopquanao.Repository.ProductDetailRepo;
import org.example.shopquanao.Repository.ProductDetailRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductDetailServices {

    private final ProductDetailRepository productDetailRepository;
    private final ProductDetailRepo productDetailRepo;
    public AdminProductDetailServices(ProductDetailRepository productDetailRepository, ProductDetailRepo productDetailRepo) {
        this.productDetailRepository = productDetailRepository;
        this.productDetailRepo = productDetailRepo;
    }

    public List<ProductDetail> getAllProductDetails() {
        return productDetailRepository.findAll();
    }

    public Page<ProductDetail> getAllProductDetailsPage(int pageNo, int pageSize,String sortField) {
        Sort sort =  Sort.by(sortField).descending() ;
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        return productDetailRepository.findAll(pageable);

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
