package com.quanan.QuanLyQuanAnBinhDan.controller;

import com.quanan.QuanLyQuanAnBinhDan.dto.CartItem;
import com.quanan.QuanLyQuanAnBinhDan.entity.*;
import com.quanan.QuanLyQuanAnBinhDan.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private DishRepository dishRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // 1. Xem giỏ hàng
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();
        
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart) {
            total = total.add(item.getSubTotal());
        }
        
        model.addAttribute("cart", cart);
        model.addAttribute("totalAmount", total);
        return "cart"; // Trỏ tới trang cart.html
    }

    // 2. Thêm món vào giỏ
    @PostMapping("/add")
    public String addToCart(@RequestParam Integer dishId, @RequestParam Integer quantity, HttpSession session) {
        Dish dish = dishRepository.findById(dishId).orElse(null);
        if (dish != null) {
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart == null) cart = new ArrayList<>();

            // Kiểm tra món đã có trong giỏ chưa
            boolean exists = false;
            for (CartItem item : cart) {
                if (item.getDishId().equals(dishId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                cart.add(new CartItem(dish.getId(), dish.getName(), dish.getPrice(), quantity));
            }
            session.setAttribute("cart", cart);
        }
        return "redirect:/?success=added"; // Quay lại trang chủ
    }

    // 3. Thanh toán (Checkout)
    @PostMapping("/checkout")
    public String checkout(@RequestParam String address, @RequestParam String phone, 
                           HttpSession session, Principal principal) {
        // Kiểm tra đăng nhập
        if (principal == null) return "redirect:/customer-login";
        
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) return "redirect:/cart";

        // Lấy thông tin khách hàng
        Customer customer = customerRepository.findByUsername(principal.getName());

        // Tạo đơn hàng mới
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus("ONLINE_PENDING"); // Đơn chờ quán xác nhận
        order.setShippingAddress(address);
        order.setCustomerPhone(phone);
        
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart) total = total.add(item.getSubTotal());
        order.setTotalAmount(total);
        
        // Lưu Order vào DB trước để lấy ID
        Order savedOrder = orderRepository.save(order);

        // Lưu từng món vào OrderDetail
        for (CartItem item : cart) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(savedOrder);
            detail.setDish(dishRepository.findById(item.getDishId()).orElse(null));
            detail.setQuantity(item.getQuantity());
            detail.setPriceAtOrder(item.getPrice());
            orderDetailRepository.save(detail);
        }

        // Xóa giỏ hàng sau khi đặt thành công
        session.removeAttribute("cart");
        return "redirect:/?success=ordered";
    }
}