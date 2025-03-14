package org.example.shopquanao.Repository;

import org.example.shopquanao.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepository extends JpaRepository<Product, Integer> {
}
