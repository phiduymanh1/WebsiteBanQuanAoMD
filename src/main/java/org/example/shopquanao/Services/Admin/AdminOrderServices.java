package org.example.shopquanao.Services.Admin;

import jakarta.transaction.Transactional;
import org.example.shopquanao.Dto.AdminDto.ViewOrdersAdminDTO;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Repository.OrderRepository;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class AdminOrderServices {
    private final OrderRepository orderRepository;

    public AdminOrderServices(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<ViewOrdersAdminDTO> getPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Order> ordersPage = orderRepository.findAll(pageable);

        return ordersPage.map(ViewOrdersAdminDTO::convertDto);
    }

    public ViewOrdersAdminDTO detail(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        Hibernate.initialize(order.getOrderItems());
        return ViewOrdersAdminDTO.convertDto(order);
    }
}
