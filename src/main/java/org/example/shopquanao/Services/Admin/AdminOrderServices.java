package org.example.shopquanao.Services.Admin;

import jakarta.transaction.Transactional;
import org.example.shopquanao.Dto.AdminDto.OrderCreateRequest;
import org.example.shopquanao.Dto.AdminDto.ViewOrdersAdminDTO;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.OrderItem;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Entity.User;
import org.example.shopquanao.Enum.OrderStatus;
import org.example.shopquanao.Repository.OrderItemRepository;
import org.example.shopquanao.Repository.OrderRepository;
import org.example.shopquanao.Repository.ProductDetailRepository;
import org.example.shopquanao.Repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@Transactional
public class AdminOrderServices {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductDetailRepository productDetailRepository;
    private final OrderItemRepository orderItemRepository;

    public AdminOrderServices(OrderRepository orderRepository, UserRepository userRepository, ProductDetailRepository productDetailRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productDetailRepository = productDetailRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Page<ViewOrdersAdminDTO> getPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        String status = OrderStatus.PENDING.toString();
        Page<Order> ordersPage = orderRepository.findAll(pageable);

        return ordersPage.map(ViewOrdersAdminDTO::convertDto);
    }

    public ViewOrdersAdminDTO detail(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        Hibernate.initialize(order.getOrderItems());
        return ViewOrdersAdminDTO.convertDto(order);
    }

    public Integer createOrder(OrderCreateRequest orderCreateRequest,AdminOrderItemServices adminOrderItemServices) {
        User user = null;
        if (orderCreateRequest.getUserId() != null){
            user = userRepository.findById(orderCreateRequest.getUserId()).orElseThrow(()
                    -> new RuntimeException("User not found"));
        }
        Order order = Order.formOrderRequest(orderCreateRequest,user,adminOrderItemServices);
        orderRepository.save(order);
        return order.getId();
    }

    public ViewOrdersAdminDTO getById(Integer id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return ViewOrdersAdminDTO.convertDto(order);
    }

//    public void updateTotalPrice(BigDecimal tongCong, Integer orderId){
//        orderRepository.UpdateTotalPrice(tongCong,orderId);
//    }
    @Transactional
    public void updateTotalPrice(Integer orderId) {
        try {
            List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
            BigDecimal tongCong = items.stream()
                    .map(OrderItem::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

            order.setTotalPrice(tongCong);

            orderRepository.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Có lỗi khi cập nhật tổng giá đơn hàng: " + e.getMessage());
        }
    }





}
