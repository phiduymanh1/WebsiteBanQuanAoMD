package org.example.shopquanao.Controller.Admin;

import org.example.shopquanao.Entity.Category;
import org.example.shopquanao.Entity.Product;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Services.Admin.AdminProductServices;
import org.example.shopquanao.Services.CategoryService;
import org.example.shopquanao.Services.ProductsServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    public Page<Product> getListProductAdmin(@RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return adminProductServices.getAllProductPage(pageable);
    }

    @ModelAttribute("listCategory")
    public List<Category> getListCategory() {
        return categoryService.findAll();
    }

    @GetMapping("/load")
    public String loadProduct(Model model, @ModelAttribute("product") Product product, @RequestParam(value = "page", defaultValue = "0")int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Product> listProduct = adminProductServices.getAllProductPage(pageable);

//        List<Product> hehe = adminProductServices.getAllProducts(pageable);
        model.addAttribute("listProductAdmin", listProduct.getContent());

        model.addAttribute("totalPages", listProduct.getTotalPages());     //
        model.addAttribute("currentPage", listProduct.getNumber()); // current page

        model.addAttribute("listCategory", categoryService.findAll());
        return "/View/SanPham/Admin/SanPhamAdmin.html";
    }

    @PostMapping("/them")
    public String them(Model model, @ModelAttribute("product") Product product)
                        {
//                            @RequestParam("imageUrl") MultipartFile file)
//        try {
//            // Kiểm tra nếu file không rỗng
//            if (!file.isEmpty()) {
//                // Lấy tên file gốc
//                String fileName = file.getOriginalFilename();
//
//                // Đường dẫn lưu file (ví dụ static/images/)
//                String uploadDir = new File("src/main/resources/static/images/").getAbsolutePath();
//
//                // Tạo file đích
//                File destination = new File(uploadDir + File.separator + fileName);
//
//                // Copy file vào static/images
//                file.transferTo(destination);
//
//                // Cập nhật lại imageUrl cho product
//                product.setImageUrl(fileName); // chỉ lưu tên file hoặc path tương đối
//            }

            // add product vào DB
            productsServices.addProduct(product);

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "redirect:/admin/product/load";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute("pd") Product product
                        ) {
//        try {
//            // Kiểm tra nếu file không rỗng
//            if (!file.isEmpty()) {
//                // Lấy tên file gốc và tạo tên file duy nhất (ví dụ thêm thời gian vào tên file)
//                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//
//                // Đường dẫn lưu file trong thư mục static/images (Sử dụng thư mục static của ứng dụng)
//                String uploadDir = new File("src/main/resources/static/images/").getAbsolutePath();  // Thư mục lưu ảnh
//
//                // Tạo file đích
//                File destination = new File(uploadDir + File.separator + fileName);
//
//                // Kiểm tra nếu file đã tồn tại, nếu có thì xóa
//                if (destination.exists()) {
//                    destination.delete();
//                }
//
//                // Copy file vào thư mục static/images
//                file.transferTo(destination);
//
//                // Cập nhật lại imageUrl cho product
//                product.setImageUrl("/images/" + fileName);  // Lưu tên file với đường dẫn tương đối
//            }

            // Cập nhật sản phẩm vào DB (giả sử phương thức này là cập nhật thay vì thêm mới)
            productsServices.addProduct(product);

//        } catch (IOException e) {
//            e.printStackTrace();
//            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải tệp. Vui lòng thử lại.");
//            return "/View/Error/403.html";  // Chuyển hướng đến một trang lỗi hoặc thông báo lỗi
//        }

        return "redirect:/admin/product/load";
    }



    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable int id) {
        Product product = productsServices.detailProduct(id);
        model.addAttribute("pd", product);
        model.addAttribute("listCategory", categoryService.findAll());

        return "/View/SanPham/Admin/SanPhamUpdateAdmin.html";
    }




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
