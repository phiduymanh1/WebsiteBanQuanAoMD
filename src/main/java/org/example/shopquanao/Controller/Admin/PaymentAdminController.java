package org.example.shopquanao.Controller.Admin;

import org.example.shopquanao.Dto.AdminDto.ViewOrdersAdminDTO;
import org.example.shopquanao.Entity.Order;
import org.example.shopquanao.Entity.OrderItem;
import org.example.shopquanao.Repository.PaymentRepository;
import org.example.shopquanao.Services.Admin.AdminOrderItemServices;
import org.example.shopquanao.Services.Admin.AdminOrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/payment")
public class PaymentAdminController {

    private final AdminOrderItemServices adminOrderItemServices;
    private final AdminOrderServices adminOrderServices;

    public PaymentAdminController(AdminOrderItemServices adminOrderItemServices, AdminOrderServices adminOrderServices) {
        this.adminOrderItemServices = adminOrderItemServices;
        this.adminOrderServices = adminOrderServices;
    }

    @GetMapping("/showThanhToan/{id}")
    public String showTrangThanhToan(@PathVariable("id") Integer id, Model model){
        ViewOrdersAdminDTO viewOrdersAdminDTO = adminOrderServices.detail(id);
        model.addAttribute("order",adminOrderItemServices.getAllOrderItems());
        model.addAttribute("orderHienTai",viewOrdersAdminDTO);
        return "View/ThanhToan/TrangThanhToan";
    }
}
