package org.example.shopquanao.Services;

import org.example.shopquanao.Entity.CartItem;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Repository.CartRepository;
import org.example.shopquanao.Repository.ProductDetailRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServices {
    private final CartRepository cartRepository;
    private final ProductDetailRepo productDetailRepo;
    public CartServices(CartRepository cartRepository, ProductDetailRepo productDetailRepo) {
        this.cartRepository = cartRepository;
        this.productDetailRepo = productDetailRepo;
    }

    public List<CartItem>findCartItemsByUser(int id) {
        return cartRepository.findCartItemsByUserId(id);
    }

    public CartItem findById(Integer id) {
        return cartRepository.findById(id).get();
    }

    public List<CartItem> getCartItemsByIds(List<Integer> ids) {
        return cartRepository.findAllByIds(ids);
    }

    public void addCartItem(int idUser, int productId, int quantity) {
        cartRepository.addCartItem(idUser, productId, quantity);
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public void updateQuantity(Integer productDetailId, int quantity) {
        // Tìm ProductDetail theo productDetailId
        ProductDetail productDetail = productDetailRepo.findById(Math.toIntExact(productDetailId))
                .orElseThrow(() -> new RuntimeException("ProductDetail not found"));

        // Tìm CartItem theo ProductDetail
        Optional<CartItem> cartItemOptional = cartRepository.findByProductDetail(productDetail);
        if (cartItemOptional.isPresent() && quantity > 0) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(quantity);  // Cập nhật số lượng sản phẩm
            cartRepository.save(cartItem);  // Lưu lại thay đổi
        }
    }


    public void removeCartItem(int idCart) {
        cartRepository.deleteById(idCart);
    }

    // Tính tổng tiền của các cart item, sử dụng BigDecimal
    public BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(cart -> cart.getProductDetail().getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cart.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean checkExistCartItem(int userId, int productDetailId) {
        Optional<CartItem> cartItemOptional = cartRepository.checkExistCartItem(userId, productDetailId);
        return cartItemOptional.isPresent();
    }

    public Integer currentQuantity(int userId, int productDetailId) {
            return cartRepository.CurrentQuantity(userId, productDetailId);
    }

}
