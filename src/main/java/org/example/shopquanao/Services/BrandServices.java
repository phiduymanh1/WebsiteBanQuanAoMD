package org.example.shopquanao.Services;

import org.example.shopquanao.Entity.Brand;
import org.example.shopquanao.Repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServices {
    private final BrandRepository brandRepository;

    public BrandServices(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Brand findById(int id) {
        return brandRepository.findById(id).get();
    }
}
