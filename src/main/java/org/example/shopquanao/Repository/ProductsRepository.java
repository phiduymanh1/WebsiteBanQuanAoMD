package org.example.shopquanao.Repository;


import org.example.shopquanao.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductsRepository extends JpaRepository<Product, Integer> {
}
