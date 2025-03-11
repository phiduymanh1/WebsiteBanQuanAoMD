package org.example.shopquanao.Controller;

import org.example.shopquanao.Dto.OrdersDTO;
import org.example.shopquanao.Services.OrderServices;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderServices orderServices;

    public OrderController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    @GetMapping("/load")
    public String orderPage(Model model,
                            @RequestParam(defaultValue = "0") int pageNo,
                            @RequestParam(defaultValue = "2") int pageSize) {
        Page<OrdersDTO> orderPage = orderServices.getPage(pageNo,pageSize);
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages",orderPage.getTotalPages());
        return "View/Order/Order";
    }
}
