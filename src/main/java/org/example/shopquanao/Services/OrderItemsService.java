package org.example.shopquanao.Services;

import lombok.RequiredArgsConstructor;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.OrderItem;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Repository.OrderItemsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderItemsService {
    private final OrderItemsRepository orderItemsRepository;

    public void createOrderItems(Integer orderId, Integer productDetailId, Integer quantity, BigDecimal price) {
        OrderItem orderItem = new OrderItem();
        Order order = new Order();
        order.setId(orderId);

        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(productDetailId);

        orderItem.setOrder(order);
        orderItem.setProductDetail(productDetail);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(price);
        orderItemsRepository.save(orderItem);
    }

}
