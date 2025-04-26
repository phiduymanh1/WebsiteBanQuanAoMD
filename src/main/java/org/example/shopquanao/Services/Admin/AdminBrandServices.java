package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Entity.Brand;
import org.example.shopquanao.Repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminBrandServices {
    private final BrandRepository brandRepository;

    public AdminBrandServices(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
