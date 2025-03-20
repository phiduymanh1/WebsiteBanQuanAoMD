package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Entity.Size;
import org.example.shopquanao.Repository.SizeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSizeServices {
    private final SizeRepository sizeRepository;

    public AdminSizeServices(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }
}
