package org.example.shopquanao.Repository;

import org.aspectj.weaver.ast.Or;
import org.example.shopquanao.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
//    public List<Order> findByCustomerId(int customerId);
}
