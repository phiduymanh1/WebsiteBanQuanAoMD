package org.example.shopquanao.Repository;

import org.example.shopquanao.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
