package org.example.shopquanao.Dto.AdminDto;

import lombok.*;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.OrderItem;
import org.example.shopquanao.Entity.User;
import org.example.shopquanao.Enum.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderCreateRequest {
    private Integer userId;

    private BigDecimal totalPrice;

    private Set<OrderItemCreateRequest> orderItem;

    private Set<PaymentCreateRequest> payment;


}
