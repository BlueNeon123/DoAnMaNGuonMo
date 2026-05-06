package com.quanan.QuanLyQuanAnBinhDan.controller;

import com.quanan.QuanLyQuanAnBinhDan.entity.Order;
import com.quanan.QuanLyQuanAnBinhDan.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderRepository orderRepository;

    // 1. Hiển thị danh sách đơn hàng Online
    @GetMapping
    public String listOnlineOrders(Model model) {
        List<Order> orders = orderRepository.findByCustomerIsNotNullOrderByOrderTimeDesc();
        model.addAttribute("orders", orders);
        return "admin_orders"; // Trỏ tới file giao diện
    }

    // 2. Xử lý khi Admin bấm nút Cập nhật trạng thái
    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam Integer id, @RequestParam String status) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status); // Cập nhật trạng thái mới
            orderRepository.save(order);
        }
        return "redirect:/admin/orders";
    }
}