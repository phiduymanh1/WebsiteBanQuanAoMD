package org.example.shopquanao.Dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.example.shopquanao.Entity.OrderItem;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemDto {

    private Integer id;

    private Integer orderId;

    private Integer productId;

    private String productName;

    private String productImage;

    private String size;

    private String color;

    private String brand;

    private Integer stock;

    private Integer quantity;

    private BigDecimal price;

    public static OrderItemDto convertDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setOrderId(orderItem.getOrder().getId());
        orderItemDto.setProductId(orderItem.getProductDetail().getId());
        orderItemDto.setProductName(orderItem.getProductDetail().getProduct().getName());
        orderItemDto.setProductImage(orderItem.getProductDetail().getProduct().getImageUrl());
        orderItemDto.setSize(orderItem.getProductDetail().getSize().getName());
        orderItemDto.setColor(orderItem.getProductDetail().getColor().getName());
        orderItemDto.setBrand(orderItem.getProductDetail().getBrand().getName());
        orderItemDto.setStock(orderItem.getProductDetail().getStock());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        return orderItemDto;
    }


}
