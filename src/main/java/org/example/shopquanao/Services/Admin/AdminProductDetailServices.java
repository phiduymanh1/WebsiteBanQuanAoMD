package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Entity.ProductDetail;
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

    public AdminProductDetailServices(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    public List<ProductDetail> findAll() {
        return productDetailRepository.findAll();
    }

    public List<ProductDetail> getAllProductDetails() {
        return productDetailRepository.findAll();
    }
    public ProductDetail findById(int id) {
        return productDetailRepository.findById(id).get();
    }

    public Page<ProductDetail> getAllProductDetailsPage(int pageNo, int pageSize,String sortField) {
        Sort sort =  Sort.by(sortField).descending() ;
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        return productDetailRepository.findAll(pageable);
    }
    public void save(ProductDetail productDetail) {
        productDetailRepository.save(productDetail);
    }

    public void delete(int id) {
        productDetailRepository.deleteById(id);
    }
}
