package org.example.shopquanao.Dto.AdminDto;

import lombok.*;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.Payment;
import org.example.shopquanao.Enum.PaymentMethod;
import org.example.shopquanao.Enum.PaymentStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentCreateRequest {
    private Integer orderId;


    private PaymentMethod paymentMethod;

    private String transactionId;


}
