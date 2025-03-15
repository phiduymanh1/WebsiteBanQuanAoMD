package org.example.shopquanao;

import org.example.shopquanao.Services.OrderServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopQuanAoApplication implements CommandLineRunner {
    private final OrderServices orderServices;

    public ShopQuanAoApplication(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShopQuanAoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        OrdersDTO ordersDTO = orderServices.detail(1);
//
//        if (ordersDTO.getOrderItems() != null && !ordersDTO.getOrderItems().isEmpty()) {
//            ordersDTO.getOrderItems().forEach(System.out::println);
//        } else {
//            System.out.println("Danh sách orderItems trống!");
//        }
    }
}
