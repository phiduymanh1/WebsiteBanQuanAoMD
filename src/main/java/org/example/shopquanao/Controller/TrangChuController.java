package org.example.shopquanao.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TrangChuController {

    @GetMapping("/trang-chu")
    public String home(){
        return "/View/Home/TrangChu.html";
    }
}
