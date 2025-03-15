package org.example.shopquanao.Dto;


import jakarta.persistence.*;
import org.example.shopquanao.Entity.Order;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OrdersDTO {

    private Integer id;
    private Integer userId;
    private String fullName;
    private BigDecimal totalPrice;
    private String status;
    private OffsetDateTime createdAt;
    private List<OrderItemDto> orderItems;
    private List<PaymentDto> payments;

    public static OrdersDTO convertDto(Order order) {
        OrdersDTO dto = new OrdersDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setFullName(order.getUser().getFullName());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus().name());
        dto.setCreatedAt(order.getCreatedAt());
        if (order.getOrderItems() != null){
            dto.setOrderItems(order.getOrderItems().stream()
                    .map(OrderItemDto::convertDto).collect(Collectors.toList()));
        }
        if (order.getPayments() != null){
            dto.setPayments(order.getPayments().stream().map(PaymentDto::convertDto).toList());
        }
        return dto;
    }

    public OrdersDTO() {
    }

    public OrdersDTO(Integer id, Integer userId, String fullName, BigDecimal totalPrice, String status, OffsetDateTime createdAt, List<OrderItemDto> orderItems, List<PaymentDto> payments) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.orderItems = orderItems;
        this.payments = payments;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public List<PaymentDto> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDto> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", orderItems=" + orderItems +
                ", payments=" + payments +
                '}';
    }
}
