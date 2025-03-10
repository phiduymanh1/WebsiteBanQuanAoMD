package org.example.shopquanao.Dto;


import jakarta.persistence.*;
import org.example.shopquanao.Entity.OrderItem;
import org.example.shopquanao.Entity.Payment;
import org.example.shopquanao.Entity.User;
import org.example.shopquanao.Enum.OrderStatus;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public class OrdersDTO {

    private Integer id;

    private User user;

    private BigDecimal totalPrice;

    private OrderStatus status;

    private OffsetDateTime createdAt;

}
