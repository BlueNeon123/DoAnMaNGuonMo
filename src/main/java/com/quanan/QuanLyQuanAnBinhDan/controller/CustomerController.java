package com.quanan.QuanLyQuanAnBinhDan.controller;

import com.quanan.QuanLyQuanAnBinhDan.entity.Customer;
import com.quanan.QuanLyQuanAnBinhDan.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 1. Hiển thị form đăng ký
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Tạo một đối tượng Customer rỗng để binding (liên kết) với form
        model.addAttribute("customer", new Customer());
        return "register"; // Trả về file register.html
    }

    /**
     * 2. Xử lý dữ liệu từ form đăng ký
     */
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("customer") Customer customer, 
                                      BindingResult result, 
                                      Model model) {
        
        // 2a. Kiểm tra xem username đã tồn tại chưa
        Customer existing = customerService.findByUsername(customer.getUsername());
        if (existing != null) {
            // Nếu tồn tại, thêm lỗi và trả về form
            result.rejectValue("username", null, "Tên đăng nhập này đã tồn tại!");
        }

        // 2b. Nếu form có lỗi (ví dụ: username đã tồn tại)
        if (result.hasErrors()) {
            model.addAttribute("customer", customer); // Gửi lại customer đã nhập
            return "register"; // Trả về lại trang register
        }

        // 2c. Nếu không có lỗi -> Lưu khách hàng
        // (Hàm save() trong CustomerService đã tự mã hóa mật khẩu)
        customerService.save(customer);

        // 2d. Trả về thông báo thành công
        model.addAttribute("registrationSuccess", true);
        return "register";
        
    }
 // ... (các import)
 // ... (class CustomerController)
 // ... (hàm @GetMapping("/register"))
 // ... (hàm @PostMapping("/register"))

     // THÊM HÀM NÀY VÀO CUỐI FILE:
     /**
      * 3. Hiển thị form đăng nhập của Khách hàng
      */
     @GetMapping("/customer-login")
     public String showCustomerLoginForm(Model model) {
         return "customer_login"; // Trả về file customer_login.html
     }

 } // <--- Dán TRƯỚC dấu } cuối cùng này
    
