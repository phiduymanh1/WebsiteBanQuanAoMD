package org.example.shopquanao.Repository;

import org.example.shopquanao.Entity.CartItem;
import org.example.shopquanao.Entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cart_items (user_id, product_detail_id, quantity)\n" +
            "VALUES \n" +
            "    (?1, ?2, ?3)",
    nativeQuery = true)
    public void addCartItem(int user_id, int product_detail_id, int quantity);

    //select cart by id user
    List<CartItem> findCartItemsByUserId(int user_id);

    //check exist cart by user and productDetail
    @Query(value = "select c from CartItem c where c.user.id = :userID and c.productDetail.id = :productDetailId")
    Optional<CartItem>checkExistCartItem(@Param("userID") int userID, @Param("productDetailId") int productDetailID);

    //get current quantity in CartItem
    @Query(value = "select c.quantity from CartItem c where c.user.id = :userId and c.productDetail.id = :productDetailId")
    Integer CurrentQuantity(@Param("userId") int userID, @Param("productDetailId") int productDetailID);


    // select CartItem by ProductDetail
    Optional<CartItem> findByProductDetail(ProductDetail productDetail);

    // select CartItem by ProductDetail
    @Query(value = "SELECT * FROM cart_items cart WHERE cart.id IN :ids", nativeQuery = true)
    List<CartItem> findAllByIds(@Param("ids") List<Integer> ids);

}
