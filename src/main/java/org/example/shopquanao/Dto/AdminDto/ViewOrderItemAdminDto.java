package org.example.shopquanao.Dto.AdminDto;


import lombok.*;
import org.example.shopquanao.Entity.OrderItem;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ViewOrderItemAdminDto {

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

    public static ViewOrderItemAdminDto convertDto(OrderItem orderItem) {
        ViewOrderItemAdminDto viewOrderItemAdminDto = new ViewOrderItemAdminDto();
        viewOrderItemAdminDto.setId(orderItem.getId());
        viewOrderItemAdminDto.setOrderId(orderItem.getOrder().getId());
        viewOrderItemAdminDto.setProductId(orderItem.getProductDetail().getId());
        viewOrderItemAdminDto.setProductName(orderItem.getProductDetail().getProduct().getName());
        viewOrderItemAdminDto.setProductImage(orderItem.getProductDetail().getImageUrl());
        viewOrderItemAdminDto.setSize(orderItem.getProductDetail().getSize().getName());
        viewOrderItemAdminDto.setColor(orderItem.getProductDetail().getColor().getName());
        viewOrderItemAdminDto.setBrand(orderItem.getProductDetail().getBrand().getName());
        viewOrderItemAdminDto.setStock(orderItem.getProductDetail().getStock());
        viewOrderItemAdminDto.setQuantity(orderItem.getQuantity());
        viewOrderItemAdminDto.setPrice(orderItem.getPrice());
        return viewOrderItemAdminDto;
    }


}
