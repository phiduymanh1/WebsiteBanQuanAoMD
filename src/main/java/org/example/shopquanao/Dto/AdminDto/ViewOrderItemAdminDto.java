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

    private Integer productDetailId;

    private String productName;

    private String productImage;

    private String size;

    private String color;

    private String brand;

    private Integer stock;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal priceProduct;

//    public static ViewOrderItemAdminDto convertDto(OrderItem orderItem) {
//        // Kiểm tra nếu orderItem hoặc các đối tượng liên quan là null
//        if (orderItem == null || orderItem.getProductDetail() == null || orderItem.getProductDetail().getProduct() == null
//                || orderItem.getProductDetail().getSize() == null || orderItem.getProductDetail().getColor() == null
//                || orderItem.getProductDetail().getBrand() == null) {
//            throw new IllegalArgumentException("Thông tin đơn hàng hoặc sản phẩm không đầy đủ");
//        }
//
//        ViewOrderItemAdminDto viewOrderItemAdminDto = new ViewOrderItemAdminDto();
//        viewOrderItemAdminDto.setId(orderItem.getId());
//        viewOrderItemAdminDto.setOrderId(orderItem.getOrder().getId());
//        viewOrderItemAdminDto.setProductDetailId(orderItem.getProductDetail().getId());
//        viewOrderItemAdminDto.setProductName(orderItem.getProductDetail().getProduct().getName());
//        viewOrderItemAdminDto.setProductImage(orderItem.getProductDetail().getImageUrl());
//        viewOrderItemAdminDto.setSize(orderItem.getProductDetail().getSize().getName());
//        viewOrderItemAdminDto.setColor(orderItem.getProductDetail().getColor().getName());
//        viewOrderItemAdminDto.setBrand(orderItem.getProductDetail().getBrand().getName());
//        viewOrderItemAdminDto.setStock(orderItem.getProductDetail().getStock());
//        viewOrderItemAdminDto.setQuantity(orderItem.getQuantity());
//        viewOrderItemAdminDto.setPrice(orderItem.getPrice());
//        viewOrderItemAdminDto.setPriceProduct(orderItem.getProductDetail().getProduct().getPrice());
//
//        return viewOrderItemAdminDto;
//    }
    public static ViewOrderItemAdminDto convertDto(OrderItem orderItem) {
    //    if (orderItem == null || orderItem.getOrder() == null || orderItem.getProductDetail() == null) {
    //        throw new IllegalArgumentException("Thông tin đơn hàng hoặc sản phẩm không đầy đủ");
    //    }

        if (orderItem == null) {
            throw new IllegalArgumentException("Order item không tồn tại.");
        }

        if (orderItem.getOrder() == null) {
            throw new IllegalArgumentException("Đơn hàng không tồn tại.");
        }

        if (orderItem.getProductDetail() == null) {
            throw new IllegalArgumentException("Chi tiết sản phẩm không tồn tại.");
        }

    // Log chi tiết về các đối tượng
        System.out.println("Order ID: " + orderItem.getOrder().getId());
        System.out.println("Product Detail ID: " + orderItem.getProductDetail().getId());

        // Log các giá trị trước khi tiếp tục
        System.out.println("Order ID: " + orderItem.getOrder().getId());
        System.out.println("Product Detail: " + orderItem.getProductDetail());

        // Còn lại các đoạn mã chuyển đổi như bình thường
        ViewOrderItemAdminDto viewOrderItemAdminDto = new ViewOrderItemAdminDto();
        viewOrderItemAdminDto.setId(orderItem.getId());
        viewOrderItemAdminDto.setOrderId(orderItem.getOrder().getId());
        viewOrderItemAdminDto.setProductDetailId(orderItem.getProductDetail().getId());


        // Kiểm tra null trước khi truy cập thuộc tính
        if (orderItem.getProductDetail().getProduct() != null) {
            viewOrderItemAdminDto.setProductName(orderItem.getProductDetail().getProduct().getName());
        }
        if (orderItem.getProductDetail().getImageUrl() != null) {
            viewOrderItemAdminDto.setProductImage(orderItem.getProductDetail().getImageUrl());
        }
        if (orderItem.getProductDetail().getSize() != null) {
            viewOrderItemAdminDto.setSize(orderItem.getProductDetail().getSize().getName());
        }
        if (orderItem.getProductDetail().getColor() != null) {
            viewOrderItemAdminDto.setColor(orderItem.getProductDetail().getColor().getName());
        }
        if (orderItem.getProductDetail().getBrand() != null) {
            viewOrderItemAdminDto.setBrand(orderItem.getProductDetail().getBrand().getName());
        }
        if (orderItem.getProductDetail().getStock() != null) {
            viewOrderItemAdminDto.setStock(orderItem.getProductDetail().getStock());
        }
        viewOrderItemAdminDto.setQuantity(orderItem.getQuantity());
        viewOrderItemAdminDto.setPrice(orderItem.getPrice());
        if (orderItem.getProductDetail().getProduct() != null) {
            viewOrderItemAdminDto.setPriceProduct(orderItem.getProductDetail().getProduct().getPrice());
        }

        return viewOrderItemAdminDto;
    }


}
