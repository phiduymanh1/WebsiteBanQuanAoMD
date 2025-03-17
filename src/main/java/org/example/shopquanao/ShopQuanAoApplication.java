package org.example.shopquanao;

import org.example.shopquanao.Dto.AdminDto.ViewOrdersAdminDTO;
import org.example.shopquanao.Services.Admin.AdminOrderServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopQuanAoApplication implements CommandLineRunner {
    private final AdminOrderServices orderServices;

    public ShopQuanAoApplication(AdminOrderServices orderServices) {
        this.orderServices = orderServices;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShopQuanAoApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

    }
}
