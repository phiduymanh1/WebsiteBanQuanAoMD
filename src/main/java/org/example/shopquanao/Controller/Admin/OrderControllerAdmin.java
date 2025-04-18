package org.example.shopquanao.Controller.Admin;

import org.example.shopquanao.Dto.AdminDto.*;
import org.example.shopquanao.Entity.ProductDetail;
import org.example.shopquanao.Enum.OrderStatus;
import org.example.shopquanao.Services.Admin.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("admin/order")
public class OrderControllerAdmin {
    private final AdminOrderServices orderServices;
    private final ColorAdminServices colorAdminServices;
    private final AdminSizeServices adminSizeServices;
    private final AdminBrandServices adminBrandServices;
    private final ProductAdminServices productAdminServices;
    private final AdminProductDetailServices adminProductDetailServices;
    private final AdminOrderItemServices orderItemServices;
    private final ProductDetailAdminServices productDetailAdminServices;
    private final AdminOrderItemServices adminOrderItemServices;
    private final AdminOrderServices adminOrderServices;

    public OrderControllerAdmin(AdminOrderServices orderServices, ColorAdminServices colorAdminServices, AdminSizeServices adminSizeServices, AdminBrandServices adminBrandServices, ProductAdminServices productAdminServices, AdminProductDetailServices adminProductDetailServices, AdminOrderItemServices orderItemServices, ProductDetailAdminServices productDetailAdminServices, AdminOrderItemServices adminOrderItemServices, AdminOrderServices adminOrderServices) {
        this.orderServices = orderServices;
        this.colorAdminServices = colorAdminServices;
        this.adminSizeServices = adminSizeServices;
        this.adminBrandServices = adminBrandServices;
        this.productAdminServices = productAdminServices;
        this.adminProductDetailServices = adminProductDetailServices;
        this.orderItemServices = orderItemServices;
        this.productDetailAdminServices = productDetailAdminServices;
        this.adminOrderItemServices = adminOrderItemServices;
        this.adminOrderServices = adminOrderServices;
    }


    @GetMapping("/load")
    public String orderPage(Model model,
                            @RequestParam(defaultValue = "0") int pageNo,
                            @RequestParam(defaultValue = "5") int pageSize) {
        Page<ViewOrdersAdminDTO> orderPage = orderServices.getPage(pageNo,pageSize);
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages",orderPage.getTotalPages());
        return "View/Order/Admin/Order";
    }

    @GetMapping("/detail/{id}")
    public String detailPage(@PathVariable("id") Integer id, Model model){
        ViewOrdersAdminDTO viewOrdersAdminDTO = orderServices.detail(id);
        model.addAttribute("ordersDTO", viewOrdersAdminDTO);
        List<OrderStatus> listStt = Arrays.asList(OrderStatus.values());
        model.addAttribute("listStt", listStt);
        model.addAttribute("listColor", colorAdminServices.getAllColors() );
        model.addAttribute("listSize", adminSizeServices.getAllSizes() );
        model.addAttribute("listBrand", adminBrandServices.getAllBrands() );
        viewOrdersAdminDTO.getOrderItems().forEach(System.out::println);

        return "View/Order/Admin/DetailOrder";
    }

    @GetMapping("/banHang/{id}")
    public String showAdd(Model model, @RequestParam(defaultValue = "0") int pageNo,
                          @RequestParam(defaultValue = "4") int pageSize,
                          @RequestParam(defaultValue = "stock") String sortFiled,
                          @PathVariable("id") Integer id){
        Page<ProductDetail> page = adminProductDetailServices.getAllProductDetailsPage(pageNo,pageSize,sortFiled);
        model.addAttribute("listProductPage", page);
        model.addAttribute("totalPage",page.getTotalPages());
        model.addAttribute("listOrderItem" , orderItemServices.getAllOrderItems());
        model.addAttribute("order", orderServices.getById(id));
        return "View/Order/Admin/AddOrder";
    }

    @GetMapping("/add")
    public String addOrder(){
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setUserId(null);
        orderCreateRequest.setTotalPrice(BigDecimal.valueOf(0));


        Integer orderId = orderServices.createOrder(orderCreateRequest,adminOrderItemServices);

        PaymentCreateRequest paymentCreateRequest = new PaymentCreateRequest();
        paymentCreateRequest.setOrderId(orderId);
        paymentCreateRequest.setPaymentMethod(null);


        return "redirect:/admin/order/load";
    }



    @PostMapping("/thanhToan")
    public String addProdetail(@RequestParam("productDetailId") Integer productDetailId,
                               @RequestParam("orderId") Integer orderId,
                               @RequestParam("quantityCash") Integer quantity){

        ViewOrdersAdminDTO viewOrdersAdminDTO = orderServices.getById(orderId);

        ProductDetail productDetail =  productDetailAdminServices.getProductDetail(productDetailId);

        ViewOrderItemAdminDto viewOrderItemAdminDtoCheck = orderItemServices.findByOrderAndProduct(orderId,productDetailId);
        if (viewOrderItemAdminDtoCheck != null) {
//            viewOrderItemAdminDtoCheck.setQuantity(viewOrderItemAdminDtoCheck.getQuantity() +1);
//            viewOrderItemAdminDtoCheck.setPrice(viewOrderItemAdminDtoCheck.getPrice().add(productDetail.getProduct().getPrice()));

            Integer newQuantity = viewOrderItemAdminDtoCheck.getQuantity() +quantity;

            BigDecimal price = productDetail.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));

            adminOrderItemServices.updateQuantity(quantity,price,orderId,productDetailId);
        }



        return "redirect:/admin/order/banHang/" +orderId;

    }
    @PostMapping("/removeItem")
    public String removeItemFromOrder(@RequestParam Integer productDetailId, @RequestParam Integer orderId, Model model) {
        orderItemServices.removeSanPhamFromOrderItem(orderId, productDetailId);

        // Sau khi xóa sản phẩm, bạn có thể chuyển hướng lại trang giỏ hàng
        return "redirect:/admin/order/banHang/" + orderId; // hoặc trang giỏ hàng của bạn
    }




}
