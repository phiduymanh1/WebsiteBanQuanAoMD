package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Entity.ProductDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductDetailServices {
    private final ProductDetailRepository productDetailRepository;

    public AdminProductDetailServices(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    public List<ProductDetail> findAll() {
        return productDetailRepo.findAll();
    }

    public List<ProductDetail> getAllProductDetails() {
        return productDetailRepository.findAll();
    public ProductDetail findById(int id) {
        return productDetailRepo.findById(id).get();
    }

    public Page<ProductDetail> getAllProductDetailsPage(int pageNo, int pageSize,String sortField) {
        Sort sort =  Sort.by(sortField).descending() ;
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        return productDetailRepository.findAll(pageable);
    public void save(ProductDetail productDetail) {
        productDetailRepo.save(productDetail);
    }

    public void delete(int id) {
        productDetailRepo.deleteById(id);
    }
}
