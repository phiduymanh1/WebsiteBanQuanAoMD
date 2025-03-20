package org.example.shopquanao.Repository;

import org.example.shopquanao.Entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    @Query(value = "SELECT DISTINCT \n" +
            "    s.id, \n" +
            "    s.name \n" +
            "FROM product_detail pd \n" +
            "JOIN sizes s ON pd.size_id = s.id \n" +
            "WHERE pd.product_id = ?1",
            nativeQuery = true)
    public List<Size> findByProductId(Integer productId);
}
