package org.example.shopquanao.Services;

import org.example.shopquanao.Repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServices {
    private final OrderRepository orderRepository;

    public OrderServices(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
