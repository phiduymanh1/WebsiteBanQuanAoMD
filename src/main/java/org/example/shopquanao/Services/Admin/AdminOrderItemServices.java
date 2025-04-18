package org.example.shopquanao.Services.Admin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.shopquanao.Dto.AdminDto.OrderCreateRequest;
import org.example.shopquanao.Dto.AdminDto.OrderItemCreateRequest;
import org.example.shopquanao.Dto.AdminDto.ViewOrderItemAdminDto;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.OrderItem;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Repository.OrderItemRepository;
import org.example.shopquanao.Repository.OrderRepository;
import org.example.shopquanao.Repository.ProductDetailRepository;
import org.example.shopquanao.Repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminOrderItemServices {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductDetailRepository productDetailRepository;

    @PersistenceContext
    private EntityManager em;

    public AdminOrderItemServices(OrderItemRepository orderItemRepository, OrderRepository orderRepository, ProductDetailRepository productDetailRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productDetailRepository = productDetailRepository;
    }

    public List<ViewOrderItemAdminDto> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream().map(ViewOrderItemAdminDto::convertDto).collect(Collectors.toList());
    }

    public OrderItem findById(Integer id){
        return orderItemRepository.findById(id).orElseThrow(()->
                new RuntimeException("OrderItem khong ton tai"));
    }

    public void addOrUpdateOrderItem(OrderItemCreateRequest orderItemCreateRequest) {
        Order order = orderRepository.findById(orderItemCreateRequest.getOrderId()).orElseThrow(()-> new RuntimeException("Order ko dc rong")) ;
        orderItemRepository.save(createOrderItemRequest(orderItemCreateRequest,order));
    }

    public ViewOrderItemAdminDto findByOrderAndProduct(Integer orderId, Integer productId) {
        OrderItem orderItem = orderItemRepository.findByOrderIdAndProductDetailId(orderId, productId);
        if (orderItem == null) {
            throw new IllegalArgumentException("Sản phẩm trong đơn hàng không tồn tại.");
        }
        return ViewOrderItemAdminDto.convertDto(orderItem);
    }

    public OrderItem createOrderItemRequest(OrderItemCreateRequest request, Order order){
        ProductDetail productDetail = productDetailRepository.findById(request.getProductDetailId()).orElseThrow(
                ()-> new RuntimeException("ProductDetail ko duoc rong"));
        return OrderItem.formCreateRequest(request,order,productDetail);
    }

    public void updateQuantity(Integer quantity, BigDecimal price, Integer orderId, Integer productDetailId){
//        orderItemRepository.updateQuantity(quantity,price,orderId,productDetailId);
//
        OrderItem orderItem = orderItemRepository.findByOrderIdAndProductDetailId(orderId, productDetailId);

        if (orderItem != null) {
            orderItem.setQuantity(quantity);
            orderItem.setPrice(price);
            // Không cần gọi save() nếu dùng trong @Transactional, Hibernate tự flush
        } else {
            throw new RuntimeException("Không tìm thấy orderItem với orderId và productDetailId đã cho");
        }
    }
    public void removeSanPhamFromOrderItem(Integer orderId, Integer productDetailId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        orderItems.stream()
                .filter(orderItem -> orderItem.getProductDetail().getId().equals(productDetailId))
                .findFirst()
                .ifPresent(orderItem -> {
                    order.getOrderItems().remove(orderItem);
                    orderItemRepository.delete(orderItem);
                });
    }


}
