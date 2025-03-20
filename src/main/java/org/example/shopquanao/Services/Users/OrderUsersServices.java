package org.example.shopquanao.Services.Users;

import org.example.shopquanao.Repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderUsersServices {
    private final OrderRepository orderRepository;

    public OrderUsersServices(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
