package org.example.shopquanao.Dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.example.shopquanao.Entity.OrderItem;

import java.math.BigDecimal;

public class OrderItemDto {

    private Integer id;

    private Integer orderId;

    private Integer productId;

    private String productName;

    private String productImage;

    private Integer quantity;

    private BigDecimal price;

    public static OrderItemDto convertDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setOrderId(orderItem.getOrder().getId());
        orderItemDto.setProductId(orderItem.getProduct().getId());
        orderItemDto.setProductName(orderItem.getProduct().getName());
        orderItemDto.setProductImage(orderItem.getProduct().getImageUrl());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        return orderItemDto;
    }

    public OrderItemDto() {
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderItemDto(Integer id, Integer orderId, Integer productId, String productName, String productImage, Integer quantity, BigDecimal price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
