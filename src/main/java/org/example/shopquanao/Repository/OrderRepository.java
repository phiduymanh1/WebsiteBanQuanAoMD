package org.example.shopquanao.Repository;

import org.aspectj.weaver.ast.Or;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Enum.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Page<Order> findByStatusNot(OrderStatus status, Pageable pageable);

}
