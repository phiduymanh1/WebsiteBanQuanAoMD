package org.example.shopquanao.Controller.User;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.shopquanao.Entity.CartItem;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Services.CartServices;
import org.example.shopquanao.Services.OrderItemsService;
import org.example.shopquanao.Services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
//@AllArgsConstructor -- dùng cho mọi fields
@RequiredArgsConstructor // chỉ áp dụng với các field được đánh dấu final
public class CartController {
    private final CartServices cartServices;
    private final OrderService orderService;
    private final OrderItemsService orderItemsService;


    @GetMapping("/load")
    public String load(Model model) {
        List<CartItem> list = cartServices.findCartItemsByUser(1);
        model.addAttribute("listCart", list);
        int quantityCart = 0;
        for (CartItem cartItem : list) {
            quantityCart += cartItem.getQuantity();
        }
        model.addAttribute("quantityCart", quantityCart);

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem c : list) {
            BigDecimal price = c.getProductDetail().getProduct().getPrice();
            int quantity = c.getQuantity();
            total = total.add(price.multiply(new BigDecimal(quantity))); // tính tổng
        }
        model.addAttribute("total", total);
        return "View/Cart/cart";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        cartServices.removeCartItem(id);
        return "redirect:/cart/load";
    }

    // Method để cập nhật số lượng sản phẩm trong giỏ hàng
    @PostMapping("/updateQuantity")
    public String updateQuantity(@RequestParam("productDetailId") Integer productDetailId, @RequestParam("quantity") int quantity) {
        cartServices.updateQuantity(productDetailId, quantity);  // Gọi service để cập nhật số lượng
        return "redirect:/cart/load";  // Quay lại trang giỏ hàng sau khi cập nhật
    }

    // Phương thức xử lý thanh toán
    @PostMapping("/checkout")
    public String checkout(@RequestParam("selectedCartIds") List<Integer> cartIds, Model model) {
        // 1. Kiểm tra danh sách ID
        if (cartIds == null || cartIds.isEmpty()) {
            model.addAttribute("error", "Không có sản phẩm nào được chọn để thanh toán!");
            return "/View/Cart/cart";
        }

        // 2. Lấy danh sách cart item từ database
        List<CartItem> cartItems = cartServices.getCartItemsByIds(cartIds);
        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Không tìm thấy sản phẩm trong giỏ hàng!");
            return "/View/Cart/cart";
        }

        // 3. Tính tổng tiền
        BigDecimal totalPrice = cartServices.calculateTotalPrice(cartItems);

        // 4. Chuyển dữ liệu sang trang xác nhận
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItems", cartItems);
        return "/View/Cart/checkout";
    }

    @PostMapping("/thanhToan")
    public String thanhToan(Integer idUser,
                            @RequestParam("totalPrice") BigDecimal totalPrice,
                            @RequestParam("quantity") List<Integer> quantity,
                            @RequestParam("price") List<BigDecimal> price,
                            @RequestParam("idProductDetail") List<Integer> idProductDetail,
                            @RequestParam("idCartItems") List<Integer> idCartItems
    ) {
        //tạo đơn hàng và lấy ra orderId vừa tạo
        Order order = orderService.createOrder(1, totalPrice);
        for (int i = 0; i < idProductDetail.size(); i++) {
            orderItemsService.createOrderItems(
                    order.getId(),
                    idProductDetail.get(i),
                    quantity.get(i),
                    price.get(i)
            );
        }
        //remove items from cart just checked out
        for(Integer id : idCartItems) {
            cartServices.removeCartItem(id);
        }
        return "/View/Cart/payment";
    }



}


