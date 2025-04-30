package org.example.shopquanao.Repository;


import org.example.shopquanao.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAll(Pageable pageable);


    @Query(value = "Select p from Product p inner join p.productDetails pd")
    Page<Product> phanTrangClient(Pageable pageable);

}
