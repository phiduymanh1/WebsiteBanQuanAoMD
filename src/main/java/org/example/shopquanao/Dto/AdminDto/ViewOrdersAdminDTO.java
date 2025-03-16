package org.example.shopquanao.Dto.AdminDto;


import lombok.*;
import org.example.shopquanao.Entity.Order;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ViewOrdersAdminDTO {

    private Integer id;
    private Integer userId;
    private String fullName;
    private BigDecimal totalPrice;
    private String status;
    private OffsetDateTime createdAt;
    private List<ViewOrderItemAdminDto> orderItems;
    private List<PaymentDto> payments;

    public static ViewOrdersAdminDTO convertDto(Order order) {
        ViewOrdersAdminDTO dto = new ViewOrdersAdminDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setFullName(order.getUser().getFullName());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus().name());
        dto.setCreatedAt(order.getCreatedAt());
        if (order.getOrderItems() != null){
            dto.setOrderItems(order.getOrderItems().stream()
                    .map(ViewOrderItemAdminDto::convertDto).collect(Collectors.toList()));
        }
        if (order.getPayments() != null){
            dto.setPayments(order.getPayments().stream().map(PaymentDto::convertDto).toList());
        }
        return dto;
    }



}
