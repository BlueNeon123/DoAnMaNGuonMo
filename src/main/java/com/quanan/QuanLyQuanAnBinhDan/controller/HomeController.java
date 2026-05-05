package com.quanan.QuanLyQuanAnBinhDan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Khi người dùng truy cập vào trang chủ ("/")
	@GetMapping("/admin")
    public String showHomePage() {
        // Nó sẽ trả về file "index.html"
        return "index";
    }
}