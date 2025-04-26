package org.example.shopquanao.Controller.Admin;

import org.example.shopquanao.Dto.AdminDto.*;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Services.Admin.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/order/")
public class OrderAdminAPIControlerr {
    private final AdminOrderServices orderServices;
    private final ColorAdminServices colorAdminServices;
    private final AdminSizeServices adminSizeServices;
    private final AdminBrandServices adminBrandServices;
    private final ProductAdminServices productAdminServices;
    private final AdminProductDetailServices adminProductDetailServices;
    private final AdminOrderItemServices orderItemServices;
    private final ProductDetailAdminServices productDetailAdminServices;
    private final AdminOrderItemServices adminOrderItemServices;
    private final AdminOrderServices adminOrderServices;

    public OrderAdminAPIControlerr(AdminOrderServices orderServices, ColorAdminServices colorAdminServices, AdminSizeServices adminSizeServices, AdminBrandServices adminBrandServices, ProductAdminServices productAdminServices, AdminProductDetailServices adminProductDetailServices, AdminOrderItemServices orderItemServices, ProductDetailAdminServices productDetailAdminServices, AdminOrderItemServices adminOrderItemServices, AdminOrderServices adminOrderServices) {
        this.orderServices = orderServices;
        this.colorAdminServices = colorAdminServices;
        this.adminSizeServices = adminSizeServices;
        this.adminBrandServices = adminBrandServices;
        this.productAdminServices = productAdminServices;
        this.adminProductDetailServices = adminProductDetailServices;
        this.orderItemServices = orderItemServices;
        this.productDetailAdminServices = productDetailAdminServices;
        this.adminOrderItemServices = adminOrderItemServices;
        this.adminOrderServices = adminOrderServices;
    }

    @PostMapping("/updateQuantity")
    public ResponseEntity<?> updateQuantity(@RequestBody UpdateQuantityRequest request) {
        Integer productDetailId = request.getProductDetailId();
        Integer orderId = request.getOrderId();
        Integer quantity = request.getQuantity();

        System.out.println("Cập nhật sản phẩm ID: " + productDetailId + " - Số lượng mới: " + quantity);

        ProductDetail productDetail = productDetailAdminServices.getProductDetail(productDetailId);
        ViewOrderItemAdminDto viewOrderItemAdminDtoCheck = orderItemServices.findByOrderAndProduct(orderId, productDetailId);

        if (viewOrderItemAdminDtoCheck != null) {
            BigDecimal price = productDetail.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
            adminOrderItemServices.updateQuantity(quantity, price, orderId, productDetailId);


            adminOrderServices.updateTotalPrice(orderId);


        }

        return ResponseEntity.ok(Map.of("message", "Số lượng đã được cập nhật", "status", "success"));
    }

    @PostMapping("/selectSanPham")
    public ResponseEntity<?> addProductToOrder(@RequestBody OrderItemCreateRequest request) {
        boolean hasError = false; // Đánh dấu có lỗi xảy ra không
        String errorMessage = "";
        try {
            Integer productDetailId = request.getProductDetailId();
            Integer orderId = request.getOrderId();
            Integer quantity = request.getQuantity();


            // Kiểm tra giá trị đầu vào
            if (productDetailId == null || orderId == null || quantity == null) {
                hasError = true;
                errorMessage = "Thông tin yêu cầu không đầy đủ";
            }

            // Kiểm tra xem đơn hàng có tồn tại không
            ViewOrdersAdminDTO viewOrdersAdminDTO = orderServices.getById(orderId);
            if (viewOrdersAdminDTO == null) {
                hasError = true;
                errorMessage = "Đơn hàng không tồn tại";
            }

            // Kiểm tra xem sản phẩm có tồn tại không
            ProductDetail productDetail = productDetailAdminServices.getProductDetail(productDetailId);
            if (productDetail == null) {
                hasError = true;
                errorMessage = "Sản phẩm không tồn tại";
            }

            if (hasError) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage); // Trả về lỗi nếu có lỗi
            }

            ViewOrderItemAdminDto viewOrderItemAdminDtoCheck = null;
            try {
                viewOrderItemAdminDtoCheck = orderItemServices.findByOrderAndProduct(orderId, productDetailId);
            } catch (IllegalArgumentException e) {
                // Không có sản phẩm trong đơn hàng, không làm gì cả
            }

            if (hasError) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage); // Trả về lỗi nếu có lỗi
            }


            if (viewOrderItemAdminDtoCheck != null) {
                // Sản phẩm đã có trong đơn hàng, cập nhật số lượng
                int newQuantity = viewOrderItemAdminDtoCheck.getQuantity() + quantity;
                request.setQuantity(newQuantity);
                request.setOrderItemId(viewOrderItemAdminDtoCheck.getId());
                request.setPrice(productDetail.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity)));
            } else {
                // Sản phẩm chưa có trong đơn hàng, thêm mới
                request.setQuantity(quantity);
                request.setPrice(productDetail.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity)));
            }

//            BigDecimal tongCong = orderItemServices.calculateTotalPriceByOrderId(orderId);



            // Thêm hoặc cập nhật sản phẩm trong giỏ hàng
            adminOrderItemServices.addOrUpdateOrderItem(request);
            adminOrderServices.updateTotalPrice(orderId);

            System.out.println("da vao day");



            return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng");
        } catch (Exception e) {
            e.printStackTrace();

            // Ghi lại thông tin lỗi chi tiết để dễ debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi thêm sản phẩm vào giỏ");
        }
    }



}
