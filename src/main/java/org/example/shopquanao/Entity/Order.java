package org.example.shopquanao.Entity;

import jakarta.persistence.*;
import org.example.shopquanao.Dto.AdminDto.OrderCreateRequest;
import org.example.shopquanao.Enum.OrderStatus;
import org.example.shopquanao.Services.Admin.AdminOrderItemServices;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id" , nullable = true)
    private User user;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Nationalized
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "order" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "order" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new LinkedHashSet<>();


    public Order() {
    }

    public Order(Integer id, User user, BigDecimal totalPrice, OrderStatus status, OffsetDateTime createdAt, Set<OrderItem> orderItems, Set<Payment> payments) {
        this.id = id;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.orderItems = orderItems;
        this.payments = payments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public static Order formOrderRequest(OrderCreateRequest request, User user,AdminOrderItemServices adminOrderItemServices){
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(request.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(OffsetDateTime.now());

        if (request.getOrderItem() != null && !request.getOrderItem().isEmpty()){
            order.setOrderItems(request.getOrderItem().stream()
                    .map(item -> adminOrderItemServices.createOrderItemRequest(item,order)).collect(Collectors.toSet()));
        }

        if (request.getPayment() != null && !request.getPayment().isEmpty()){
            order.setPayments(request.getPayment().stream()
                    .map(payment -> Payment.fromCreateRequest(payment,order)).collect(Collectors.toSet()));
        }
        return order;


    }
}