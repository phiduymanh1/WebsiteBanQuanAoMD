package org.example.shopquanao.Controller.Admin;

import org.example.shopquanao.Entity.*;
import org.example.shopquanao.Services.Admin.AdminProductDetailServices;
import org.example.shopquanao.Services.Admin.AdminProductServices;
import org.example.shopquanao.Services.BrandServices;
import org.example.shopquanao.Services.ColorServices;
import org.example.shopquanao.Services.SizeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/productDetail")
public class ProductDetailController {
    private final AdminProductDetailServices adminProductDetailServices;
    private final ColorServices colorServices;
    private final SizeService sizeService;
    private final BrandServices brandServices;
    private final AdminProductDetailServices adminProductDetailsServices;
    private final AdminProductServices adminProductServices;

    public ProductDetailController(AdminProductDetailServices adminProductDetailServices, ColorServices colorServices, SizeService sizeService, BrandServices brandServices, AdminProductDetailServices adminProductDetailsServices, AdminProductServices adminProductServices) {
        this.adminProductDetailServices = adminProductDetailServices;
        this.colorServices = colorServices;
        this.sizeService = sizeService;
        this.brandServices = brandServices;
        this.adminProductDetailsServices = adminProductDetailsServices;
        this.adminProductServices = adminProductServices;
    }


    @ModelAttribute("listSize")
    public List<Size> getListSize() {
        return sizeService.getAll();
    }
    @ModelAttribute("listColor")
    public List<Color> getListColor() {
        return colorServices.findAll();
    }
    @ModelAttribute("listBrand")
    public List<Brand> getListBrand() {
        return brandServices.findAll();
    }

    @ModelAttribute("listProduct")
    public List<Product> getListProduct() {
        return adminProductServices.getAllProducts();
    }

    @ModelAttribute("listProductDetailAdmin")
    public List<ProductDetail> getListProductDetailAdmin() {
        return adminProductDetailServices.findAll();
    }


    @GetMapping("/load")
    public String load(Model model, @ModelAttribute("pd") ProductDetail productDetail) {
        List<ProductDetail> list = adminProductDetailServices.findAll();
        model.addAttribute("listProductDetailAdmin", list);
        return "/View/SanPham/Admin/SanPhamChiTietAdmin";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model) {
        ProductDetail productDetail = adminProductDetailServices.findById(id);
        model.addAttribute("pd", productDetail);
        return "/View/SanPham/Admin/SanPhamChiTietAdmin";
    }

    @PostMapping("/them")
    public String them( @ModelAttribute("pd") ProductDetail productDetail) {
        adminProductDetailServices.save(productDetail);
        return "redirect:/admin/productDetail/load";
    }
}
