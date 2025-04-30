package org.example.shopquanao.Services;

import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Repository.ProductDetailRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailService {
    private final ProductDetailRepo productDetailRepo;

    public ProductDetailService(ProductDetailRepo sanPhamDetailRepo) {
        productDetailRepo = sanPhamDetailRepo;
    }

    public List<ProductDetail> getAllProductDetails() {
        return productDetailRepo.findAll();
    }

    public ProductDetail getProductDetail(int id) {
        return productDetailRepo.findById(id).get();
    }

    public int getStock(int productId, int sizeId, int colorId, int brandId) {
        // Tìm sản phẩm theo product_id, color_id và size_id
        ProductDetail productDetail = productDetailRepo.findProductDetailByProductIdAndSizeIdAndColorIdAndBrandId(productId, sizeId, colorId, brandId);
        // Nếu tìm thấy sản phẩm, trả về số lượng tồn kho
        if (productDetail != null) {
            System.out.println(productDetail);
            return productDetail.getStock();
        }
        return 0;
    }

    public int getProductDetailId(int productId, int sizeId, int colorId, int brandId) {
        ProductDetail productDetail = productDetailRepo.findProductDetailByProductIdAndSizeIdAndColorIdAndBrandId(productId, sizeId, colorId, brandId);
        if (productDetail != null) {
            return productDetail.getId();
        }
        System.out.println("id san pham chi tiet: " + productDetail.getId());
        return 0;
    }


}
