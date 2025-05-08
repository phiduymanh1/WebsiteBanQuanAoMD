package org.example.shopquanao.Repository;

import org.example.shopquanao.Entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailRepo extends JpaRepository<ProductDetail, Integer> {
    //get stock by productDetailId, colorId, sizeId
//    Optional<ProductDetail> findByProductIdAndColorIdAndSizeId(int productId, int colorId, int sizeId);


    ProductDetail findProductDetailByProductIdAndSizeIdAndColorIdAndBrandId(int productId, int sizeId, int colorId, int brandId);

    Page<ProductDetail> findAll(Pageable pageable);


    // Tìm kiếm sản phẩm theo tên với phân trang
    @Query("SELECT pd FROM ProductDetail pd WHERE LOWER(pd.product.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ProductDetail> findByProductNameContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT pd FROM ProductDetail pd WHERE LOWER(pd.product.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ProductDetail> findByProductNameContainingIgnoreCase1(@Param("keyword") String keyword);



}
