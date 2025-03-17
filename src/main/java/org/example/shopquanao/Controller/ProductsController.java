package org.example.shopquanao.Controller;

import org.example.shopquanao.Entity.Product;
import org.example.shopquanao.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.StampedLock;

@Controller
@RequestMapping("/sanPham")
public class ProductsController {


    private final ProductsServices productsServices;
    private final ProductDetailService productDetailService;
    private final ColorServices colorServices;
    private final SizeService sizeService;
    private final BrandServices brandServices;


    public ProductsController(ProductsServices productsServices, ProductDetailService productDetailService, ColorServices colorServices, SizeService sizeService, BrandServices brandServices) {
        this.productsServices = productsServices;
        this.productDetailService = productDetailService;
        this.colorServices = colorServices;
        this.sizeService = sizeService;
        this.brandServices = brandServices;
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
        model.addAttribute("brand",brandServices.findById(brandId));
        return "/View/SanPham/SanPhamDetail.html";
    }
}
