package org.example.shopquanao.Dto;

import jakarta.persistence.*;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.Payment;
import org.example.shopquanao.Enum.PaymentMethod;
import org.example.shopquanao.Enum.PaymentStatus;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

public class PaymentDto {

    private Integer id;

    private Integer orderId;

    private String paymentMethod;

    private String status;

    private String transactionId;

    public static PaymentDto convertDto(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setOrderId(payment.getOrder().getId());
        paymentDto.setPaymentMethod(payment.getPaymentMethod().name());
        paymentDto.setStatus(payment.getStatus().name());
        paymentDto.setTransactionId(payment.getTransactionId());
        return paymentDto;
    }

    public PaymentDto(Integer id, Integer orderId, String paymentMethod, String status, String transactionId) {
        this.id = id;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.transactionId = transactionId;
    }

    public PaymentDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
