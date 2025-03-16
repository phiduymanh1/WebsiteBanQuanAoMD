package org.example.shopquanao.Repository;

import org.example.shopquanao.Entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    @Query(value = "SELECT DISTINCT c.id, c.name FROM colors c \n" +
            "JOIN product_detail pd ON c.id = pd.color_id \n" +
            "WHERE pd.product_id = ?1", nativeQuery = true)
    public List<Color> findByProductId(int productId);
}
