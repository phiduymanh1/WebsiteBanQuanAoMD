package org.example.shopquanao.Services.Admin;

import org.example.shopquanao.Dto.AdminDto.PaymentCreateRequest;
import org.example.shopquanao.Dto.AdminDto.PaymentDto;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.Payment;
import org.example.shopquanao.Repository.OrderRepository;
import org.example.shopquanao.Repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminPaymentServices {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public AdminPaymentServices(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public PaymentDto createPayment(PaymentCreateRequest paymentCreateRequest){
        Payment payment = new Payment();
        Order order = orderRepository.findById(paymentCreateRequest.getOrderId()).orElseThrow();
        payment.setOrder(order);

        payment.setPaymentMethod(paymentCreateRequest.getPaymentMethod());

        payment.setTransactionId(UUID.randomUUID().toString());


        return PaymentDto.convertDto(payment);
    }
}
