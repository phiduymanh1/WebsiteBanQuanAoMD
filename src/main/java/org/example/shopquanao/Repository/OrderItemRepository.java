package org.example.shopquanao.Repository;

import jakarta.transaction.Transactional;
import org.example.shopquanao.Dto.AdminDto.ViewOrderItemAdminDto;
import org.example.shopquanao.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    OrderItem findByOrderIdAndProductDetailId(Integer orderId, Integer productDetailId);

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE order_items SET quantity = :quantity, price = :price WHERE order_id = :orderId AND product_detail_id = :productDetailId",
//            nativeQuery = true)
//    void updateQuantity(@Param("quantity") Integer quantity,
//                        @Param("price") BigDecimal price,
//                        @Param("orderId") Integer orderId,
//                        @Param("productDetailId") Integer productDetailId);

    List<OrderItem> findByOrderId(Integer orderId);
}
