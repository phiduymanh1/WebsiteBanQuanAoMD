package org.example.shopquanao.Services;

import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.User;
import org.example.shopquanao.Enum.OrderStatus;
import org.example.shopquanao.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;


@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(int idUser, BigDecimal totalPrice) {
        Order order = new Order();
        User user = new User();
        user.setId(idUser);

        order.setUser(user);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(OffsetDateTime.now());
        return orderRepository.save(order);
    }

    public Order getOrder(int id) {
        return orderRepository.findById(id).get();
    }
}
