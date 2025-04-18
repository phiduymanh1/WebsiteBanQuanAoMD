package org.example.shopquanao.Dto.AdminDto;

import lombok.*;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.OrderItem;
import org.example.shopquanao.Entity.ProductDetail;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemCreateRequest {

    private Integer orderItemId;

    private Integer orderId;

    private Integer productDetailId;

    private Integer quantity;

    private BigDecimal price;


}
