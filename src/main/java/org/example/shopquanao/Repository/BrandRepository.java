package org.example.shopquanao.Repository;

import org.example.shopquanao.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("select b from ProductDetail pd " +
            "join Brand b on b.id = pd.brand.id " +
            "where pd.product.id = ?1")
    List<Brand> findByProductId(int productId);
}
