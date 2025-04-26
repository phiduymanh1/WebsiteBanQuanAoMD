package org.example.shopquanao.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.shopquanao.Dto.AdminDto.OrderItemCreateRequest;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    public OrderItem() {
    }

    public OrderItem(Integer id, Order order, ProductDetail productDetail, Integer quantity, BigDecimal price) {
        this.id = id;
        this.order = order;
        this.productDetail = productDetail;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
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

    public static OrderItem formCreateRequest(OrderItemCreateRequest request, Order order ,ProductDetail productDetail) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setId(request.getOrderItemId());
        orderItem.setProductDetail(productDetail);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setPrice(request.getPrice());

        return orderItem;
    }
}