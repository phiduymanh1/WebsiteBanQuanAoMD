package org.example.shopquanao.Controller.Admin;

import org.example.shopquanao.Entity.*;
import org.example.shopquanao.Services.Admin.AdminProductDetailServices;
import org.example.shopquanao.Services.Admin.AdminProductServices;
import org.example.shopquanao.Services.BrandServices;
import org.example.shopquanao.Services.ColorServices;
import org.example.shopquanao.Services.ProductDetailService;
import org.example.shopquanao.Services.SizeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/productDetail")
public class ProductDetailControllerAdmin {
    private final AdminProductDetailServices adminProductDetailServices;
    private final ColorServices colorServices;
    private final SizeService sizeService;
    private final BrandServices brandServices;
    private final AdminProductServices adminProductServices;
    private final ProductDetailService productDetailService;

    public ProductDetailControllerAdmin(AdminProductDetailServices adminProductDetailServices, ColorServices colorServices, SizeService sizeService, BrandServices brandServices, AdminProductServices adminProductServices, ProductDetailService productDetailService) {
        this.adminProductDetailServices = adminProductDetailServices;
        this.colorServices = colorServices;
        this.sizeService = sizeService;
        this.brandServices = brandServices;
        this.adminProductServices = adminProductServices;
        this.productDetailService = productDetailService;
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
        return adminProductServices.findAll();
    }

    @GetMapping("/load")
    public String load(Model model, @ModelAttribute("pd") ProductDetail productDetail,
                       @RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10); // Mỗi trang hiển thị 10 sản phẩm
        Page<ProductDetail> list = adminProductDetailServices.getProductDetail(pageable);

        // Thêm các thông tin phân trang vào model
        model.addAttribute("listProductDetailAdmin", list.getContent());
        model.addAttribute("currentPage", page); // Trang hiện tại
        model.addAttribute("totalPages", list.getTotalPages()); // Tổng số trang
        model.addAttribute("totalItems", list.getTotalElements()); // Tổng số phần tử

        return "/View/SanPham/Admin/SanPhamChiTietAdmin";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        ProductDetail productDetail = adminProductDetailServices.findById(id);
        Pageable pageable = PageRequest.of(page, 10); // Phân trang cho chi tiết sản phẩm
        Page<ProductDetail> list = adminProductDetailServices.getProductDetail(pageable);

        model.addAttribute("pd", productDetail);

        model.addAttribute("listProductDetailAdmin", list); // Dữ liệu sản phẩm chi tiết
        model.addAttribute("currentPage", page); // Trang hiện tại
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("totalItems", list.getTotalElements()); // Tổng số phần tử

        return "/View/SanPham/Admin/SanPhamChiTietAdmin"; // Hiển thị trang chi tiết với phân trang
    }

    @PostMapping("/them")
    public String them(@ModelAttribute("pd") ProductDetail productDetail) {
        adminProductDetailServices.save(productDetail);
        return "redirect:/admin/productDetail/load";
    }


//    @GetMapping("/search")
//    public ResponseEntity<Page<ProductDetail>> searchProductDetails(@RequestParam String keyword, Pageable pageable) {
//        Page<ProductDetail> results = adminProductDetailServices.searchProduct(keyword, pageable);
//        return ResponseEntity.ok(results);
//    }

    @GetMapping("/search")
    @ResponseBody
    public List<Map<String, Object>> searchProduct(@RequestParam("keyword") String keyword) {
        List<ProductDetail> list = adminProductDetailServices.searchName(keyword);

        List<Map<String, Object>> result = new ArrayList<>();
        for (ProductDetail p : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("productName", p.getProduct().getName());
            map.put("brandName", p.getBrand().getName());
            map.put("colorName", p.getColor().getName());
            map.put("sizeName", p.getSize().getName());
            map.put("stock", p.getStock());
            map.put("imageUrl", p.getImageUrl());
            result.add(map);
        }

        return result;
    }



}

