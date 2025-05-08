package org.example.shopquanao.Controller.User;

import jakarta.servlet.http.HttpServletResponse;
import org.example.shopquanao.Entity.CartItem;
import org.example.shopquanao.Entity.Product;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String hienThi(Model model, @ModelAttribute("sanPham") Product product,
                          @RequestParam(value = "page", defaultValue = "0") int page
                          ) {
        Pageable pageable = PageRequest.of(page,20);
        Page<Product> products = productsServices.getProducts(pageable);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItems", products.getTotalElements());
        return "View/SanPham/SanPham";
    }


//    @GetMapping("/detail/{id}/{brandId}")
//    public String detail(Model model, @PathVariable int id, @PathVariable int brandId) {
//        Product products = productsServices.detailProduct(id);
//        model.addAttribute("sanPham", products);
//
//        model.addAttribute("colors", colorServices.distinctColor(id));
//        model.addAttribute("size", sizeService.distinctSize(id));
//        model.addAttribute("brand", brandServices.findById(brandId));
//        return "/View/SanPham/SanPhamDetail";
//    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable int id) {
        Product products = productsServices.detailProduct(id);
        model.addAttribute("sanPham", products);

        model.addAttribute("colors", colorServices.distinctColor(id));
        model.addAttribute("sizes", sizeService.distinctSize(id));
        model.addAttribute("brands", brandServices.distinctBrand(id));
        return "/View/SanPham/SanPhamDetail";
    }

    @GetMapping("/api/getStock")
    public ResponseEntity<Integer> getStock(
            @RequestParam int productId,
            @RequestParam int colorId,
            @RequestParam int sizeId,
            @RequestParam int brandId) {

        int stock = productDetailService.getStock(productId, sizeId, colorId, brandId);

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
            @RequestParam("brands") int brandId,
            @RequestParam("soLuongMua") int soLuong,
            RedirectAttributes redirectAttributes
    ) {
        System.out.println("color: " + colorId);
        System.out.println("size: " + sizeId);

        int stockTotals = productDetailService.getStock(productId, sizeId, colorId, brandId);
        int idProDetail = productDetailService.getProductDetailId(productId, sizeId, colorId, brandId);

        System.out.println("idprodetail: "+ idProDetail);
        boolean checkExist = cartServices.checkExistCartItem(1,idProDetail);
        if(checkExist) {
            int currentQuantity = cartServices.currentQuantity(1,idProDetail);

            if(currentQuantity + soLuong > stockTotals) {
                redirectAttributes.addFlashAttribute("message", "sản phẩm bạn có "+ currentQuantity + " sản phẩm trong giỏ hàng. Không thể thêm số lượng đã chọn vào giỏ hàng vì sẽ vượt quá giới hạn sản phẩm trong kho ");
                return "redirect:/sanPham/detail/" + productId;
            }
            else {
                int quantityNew = currentQuantity + soLuong;
                cartServices.updateQuantity(idProDetail,quantityNew);
            }

//            cartServices.addCartItem(1, idProDetail, currentQuantity + soLuong);
            return "redirect:/cart/load";
        }
        else {
            if (idProDetail > 0) {
                cartServices.addCartItem(1, idProDetail, soLuong);
            }
        }
        return "redirect:/cart/load";
    }
}
