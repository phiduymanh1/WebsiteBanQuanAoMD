package org.example.shopquanao.Services;

import org.example.shopquanao.Entity.Size;
import org.example.shopquanao.Repository.SizeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService {
    private final SizeRepository sizeRepository;
    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public List<Size>getAll() {
        return sizeRepository.findAll();
    }
}
