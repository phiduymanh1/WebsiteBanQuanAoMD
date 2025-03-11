package org.example.shopquanao.Services;

import org.example.shopquanao.Dto.OrderItemDto;
import org.example.shopquanao.Dto.OrdersDTO;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServices {
    private final OrderRepository orderRepository;

    public OrderServices(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<OrdersDTO> getPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Order> ordersPage = orderRepository.findAll(pageable);
        return ordersPage.map(OrdersDTO::convertDto);
    }


}
