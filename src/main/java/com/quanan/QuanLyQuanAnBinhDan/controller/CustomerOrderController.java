package com.quanan.QuanLyQuanAnBinhDan.controller;

import com.quanan.QuanLyQuanAnBinhDan.entity.Customer;
import com.quanan.QuanLyQuanAnBinhDan.entity.Order;
import com.quanan.QuanLyQuanAnBinhDan.repository.CustomerRepository;
import com.quanan.QuanLyQuanAnBinhDan.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class CustomerOrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/order-history")
    public String viewOrderHistory(Model model, Principal principal) {
        // 1. Kiểm tra nếu chưa đăng nhập thì đuổi về trang login
        if (principal == null) {
            return "redirect:/customer-login";
        }

        // 2. Lấy thông tin khách hàng đang đăng nhập từ Database
        Customer customer = customerRepository.findByUsername(principal.getName());

        // 3. Lấy danh sách toàn bộ lịch sử đơn hàng của người này
        List<Order> orders = orderRepository.findByCustomerOrderByOrderTimeDesc(customer);

        // 4. Gửi sang giao diện
        model.addAttribute("orders", orders);
        return "order_history"; // Trỏ tới file order_history.html
    }
}