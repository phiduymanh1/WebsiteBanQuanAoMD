package org.example.shopquanao.Controller.Admin;

import org.example.shopquanao.Dto.AdminDto.ViewOrdersAdminDTO;
import org.example.shopquanao.Enum.OrderStatus;
import org.example.shopquanao.Services.Admin.AdminOrderServices;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("admin/order")
public class OrderControllerAdmin {
    private final AdminOrderServices orderServices;

    public OrderControllerAdmin(AdminOrderServices orderServices) {
        this.orderServices = orderServices;
    }

    @GetMapping("/load")
    public String orderPage(Model model,
                            @RequestParam(defaultValue = "0") int pageNo,
                            @RequestParam(defaultValue = "4") int pageSize) {
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
        viewOrdersAdminDTO .getOrderItems().forEach(System.out::println);

        return "View/Order/Admin/DetailOrder";
    }
}
