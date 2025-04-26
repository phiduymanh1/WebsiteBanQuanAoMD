package org.example.shopquanao.Controller.Admin;

import org.example.shopquanao.Entity.Category;
import org.example.shopquanao.Entity.Product;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Services.Admin.AdminProductServices;
import org.example.shopquanao.Services.CategoryService;
import org.example.shopquanao.Services.ProductsServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class ProductControllerAdmin {

    private final AdminProductServices adminProductServices;
    private final CategoryService categoryService;
    private final ProductsServices productsServices;

    public ProductControllerAdmin(AdminProductServices adminProductServices, CategoryService categoryService, ProductsServices productsServices) {
        this.adminProductServices = adminProductServices;
        this.categoryService = categoryService;
        this.productsServices = productsServices;
    }

    @ModelAttribute("listProductAdmin")
    public List<Product> getListProductAdmin() {
        return adminProductServices.getAllProducts();
    }

    @ModelAttribute("listCategory")
    public List<Category> getListCategory() {
        return categoryService.findAll();
    }

    @GetMapping("/load")
    public String loadProduct(Model model, @ModelAttribute("product") Product product) {
        List<Product> listProduct = adminProductServices.getAllProducts();
        model.addAttribute("listProductAdmin", listProduct);
        model.addAttribute("listCategory", categoryService.findAll());
        return "/View/SanPham/Admin/SanPhamAdmin.html";
    }

    @PostMapping("/them")
    public String them(Model model, @ModelAttribute("product") Product product) {
        productsServices.addProduct(product);
        return "redirect:/admin/product/load";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable int id) {
        Product product = productsServices.detailProduct(id);
        model.addAttribute("product", product);
        return "/View/SanPham/Admin/SanPhamAdmin";


//    @GetMapping("/detail/{id}")
//    public ResponseEntity<Product> getProductDetail(@PathVariable int id) {
//        Product product = adminProductServices.getProductById(id);  // Lấy sản phẩm từ cơ sở dữ liệu
//
//        if (product != null) {
//            return ResponseEntity.ok(product);  // Trả về thông tin sản phẩm dưới dạng JSON
//        } else {
//            return ResponseEntity.notFound().build();  // Nếu không tìm thấy, trả về 404
//        }
//    }

    }
}
