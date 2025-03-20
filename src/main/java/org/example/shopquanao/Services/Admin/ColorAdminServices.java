package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Entity.Color;
import org.example.shopquanao.Repository.ColorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorAdminServices {
    private final ColorRepository colorRepository;

    public ColorAdminServices(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }
}
