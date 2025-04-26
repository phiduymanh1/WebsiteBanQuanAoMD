package org.example.shopquanao.Controller.User;

import jakarta.servlet.http.HttpServletResponse;
import org.example.shopquanao.Entity.CartItem;
import org.example.shopquanao.Entity.Product;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sanPham")
public class ProductsController {


    private final ProductsServices productsServices;
    private final ProductDetailService productDetailService;
    private final ColorServices colorServices;
    private final SizeService sizeService;
    private final BrandServices brandServices;
    private final CartServices cartServices;


    public ProductsController(ProductsServices productsServices, ProductDetailService productDetailService, ColorServices colorServices, SizeService sizeService, BrandServices brandServices, CartServices cartServices) {
        this.productsServices = productsServices;
        this.productDetailService = productDetailService;
        this.colorServices = colorServices;
        this.sizeService = sizeService;
        this.brandServices = brandServices;
        this.cartServices = cartServices;
    }

//    @ModelAttribute("colors")
//    public List<ColorsDTO> colors(@PathVariable int idsp) {
//        return colorsDtoRepo.findByProductId(idsp);
//    }
//
//    @ModelAttribute("size")
//    public List<SizeDTO> sizes(@PathVariable int idsp) {
//        return sizeDtoRepo.findByProductId(idsp);
//    }


    @GetMapping("/hienThi")
    public String hienThi(Model model, @ModelAttribute("sanPham") Product product) {
        List<Product> products = productsServices.getProducts();
        model.addAttribute("products", products);
        return "View/SanPham/SanPham";
    }


    @GetMapping("/detail/{id}/{brandId}")
    public String detail(Model model, @PathVariable int id, @PathVariable int brandId) {
        Product products = productsServices.detailProduct(id);
        model.addAttribute("sanPham", products);

        model.addAttribute("colors", colorServices.distinctColor(id));
        model.addAttribute("size", sizeService.distinctSize(id));
        model.addAttribute("brand", brandServices.findById(brandId));
        return "/View/SanPham/SanPhamDetail";
    }

    @GetMapping("/api/getStock")
    public ResponseEntity<Integer> getStock(
            @RequestParam int productId,
            @RequestParam int colorId,
            @RequestParam int sizeId,
            @RequestParam int brandId) {

        int stock = productDetailService.getStock(productId, colorId, sizeId, brandId);

        if (stock >= 0) {
            return ResponseEntity.ok(stock);  // Trả về số lượng tồn kho trực tiếp
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addToCart")
    public String addToCart(
            @RequestParam("productId") int productId,
            @RequestParam("colors") int colorId,
            @RequestParam("size") int sizeId,
            @RequestParam("idBrand") int brandId,
            @RequestParam("soLuongMua") int soLuong
    ) {

        int idProDetail = productDetailService.getProductDetailId(productId, colorId, sizeId, brandId);
        System.out.println(idProDetail);
        boolean checkExist = cartServices.checkExistCartItem(1,idProDetail);
        if(checkExist) {
            int currentQuantity = cartServices.currentQuantity(1,idProDetail);
            cartServices.updateQuantity(idProDetail,currentQuantity + soLuong);
//            cartServices.addCartItem(1, idProDetail, currentQuantity + soLuong);
            return "redirect:/cart/load";
        }
        else {
            if (idProDetail > 0) {
                cartServices.addCartItem(1, idProDetail, soLuong);
                return "redirect:/cart/load";
            }
        }
        return "redirect:/cart/load";
    }
}
