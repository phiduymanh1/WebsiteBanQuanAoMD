package org.example.shopquanao.Controller.User;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.shopquanao.Entity.CartItem;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Repository.ProductDetailRepo;
import org.example.shopquanao.Services.CartServices;
import org.example.shopquanao.Services.OrderItemsService;
import org.example.shopquanao.Services.OrderService;
import org.example.shopquanao.Services.ProductDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final ProductDetailService productDetailService;


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
    public String updateQuantity(@RequestParam("productDetailId") Integer productDetailId,
                                 @RequestParam("quantity") int quantity,
                                 RedirectAttributes redirectAttributes
                                 ) {

        int productDetailQuantityTotal = productDetailService.getProductDetail(productDetailId).getStock();
        if(quantity > productDetailQuantityTotal) {
            quantity = productDetailQuantityTotal;
            redirectAttributes.addFlashAttribute("message", "tổng số lượng sản phẩm trong kho còn lại là " + productDetailQuantityTotal + " sản phẩm");
            cartServices.updateQuantity(productDetailId, quantity);
        }
        else {
            cartServices.updateQuantity(productDetailId, quantity);  // Gọi service để cập nhật số lượng
        }
        return "redirect:/cart/load";  // Quay lại trang giỏ hàng sau khi cập nhật
    }

    // Phương thức xử lý thanh toán
    @PostMapping("/checkout")
    public String checkout(@RequestParam("selectedCartIds") List<Integer> cartIds, Model model, RedirectAttributes redirectAttributes) {
        // 1. Kiểm tra danh sách ID
        if (cartIds == null || cartIds.isEmpty()) {
            model.addAttribute("error", "Không có sản phẩm nào được chọn để thanh toán!");
            return "/View/Cart/cart";
        }

        // 2. Lấy danh sách cart item từ database
        List<CartItem> cartItems = cartServices.getCartItemsByIds(cartIds);


        for(CartItem cartItem: cartItems) {
            ProductDetail productDetail = cartItem.getProductDetail();

            int productDetailId = cartItem.getProductDetail().getId();
            int quantityInCart = cartItem.getQuantity();

            int quantityTotal = productDetailService.getProductDetail(productDetailId).getStock();

            if(quantityInCart > quantityTotal) {
                redirectAttributes.addFlashAttribute("message", "Sản phẩm: " + productDetail.getProduct().getName() + " |" +
                        " brand: " + productDetail.getBrand().getName() + " |" +
                        " color: " + productDetail.getColor().getName() + " |" +
                        " size: " + productDetail.getSize().getName() +
                        " trong giỏ hàng không đủ số lượng. Tồn kho hiện tại là " + quantityTotal);
                return "redirect:/cart/load";
            }
        }

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


