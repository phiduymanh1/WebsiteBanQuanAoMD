package org.example.shopquanao.Controller;

import org.example.shopquanao.Entity.Color;
import org.example.shopquanao.Entity.Product;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Entity.Size;
import org.example.shopquanao.Services.ColorServices;
import org.example.shopquanao.Services.ProductDetailService;
import org.example.shopquanao.Services.ProductsServices;
import org.example.shopquanao.Services.SizeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/sanPham")
public class ProductsController {

    private final ProductsServices productsServices;
    private final ProductDetailService productDetailService;
    private final ColorServices colorServices;
    private final SizeService sizeService;

    public ProductsController(ProductsServices productsServices, ProductDetailService productDetailService, ColorServices colorServices, SizeService sizeService) {
        this.productsServices = productsServices;
        this.productDetailService = productDetailService;
        this.colorServices = colorServices;
        this.sizeService = sizeService;
    }

    @ModelAttribute("colors")
    public List<Color> colors() {
        return colorServices.findAll();
    }

    @ModelAttribute("size")
    public List<Size> sizes() {
        return sizeService.getAll();
    }


    @GetMapping("/hienThi")
    public String hienThi(Model model, @ModelAttribute("sanPham") Product product) {
        List<Product> products = productsServices.getProducts();
        model.addAttribute("products", products);
        return "/View/SanPham/SanPham.html";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable int id) {
        Product products = productsServices.detailProduct(id);
        Set<ProductDetail> productDetails = products.getProductDetails();
        model.addAttribute("productDetails", productDetails);
        model.addAttribute("sanPham", products);
        model.addAttribute("colors", colorServices.findAll());
        model.addAttribute("size", sizeService.getAll());
        return "/View/SanPham/SanPhamDetail.html";
    }
}
