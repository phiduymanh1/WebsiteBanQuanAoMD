package org.example.shopquanao.Repository;

import org.example.shopquanao.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
