package org.example.shopquanao.Repository;

import org.example.shopquanao.Entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailRepo extends JpaRepository<ProductDetail, Integer> {
    //get stock by productDetailId, colorId, sizeId
//    Optional<ProductDetail> findByProductIdAndColorIdAndSizeId(int productId, int colorId, int sizeId);


    ProductDetail findProductDetailByProductIdAndSizeIdAndColorIdAndBrandId(int productId, int sizeId, int colorId, int brandId);



}
