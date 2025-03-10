package org.example.shopquanao.Services;

import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OrderServices {
    private final OrderRepository orderRepository;

    public OrderServices(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public void findAll(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

    }
}
