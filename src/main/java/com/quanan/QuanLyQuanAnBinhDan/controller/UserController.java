package com.quanan.QuanLyQuanAnBinhDan.controller;

import com.quanan.QuanLyQuanAnBinhDan.entity.User;
import com.quanan.QuanLyQuanAnBinhDan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // 1. Hiển thị danh sách User
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        // Trả về file users_list.html
        return "users_list"; 
    }

    // 2. Hiển thị form Thêm mới
    @GetMapping("/users/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        // Trả về file user_form.html
        return "user_form";
    }

    // 3. Hiển thị form Sửa
    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id);
        // Quan trọng: Không gửi mật khẩu đã mã hóa ra view
        user.setPassword(""); // Xóa mật khẩu cũ khi edit
        model.addAttribute("user", user);
        return "user_form";
    }

    // 4. Lưu (Thêm mới hoặc Sửa)
    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute("user") User user) {
        // Hàm saveUser trong Service sẽ tự động mã hóa mật khẩu
        userService.saveUser(user);
        return "redirect:/users";
    }

    // 5. Xóa
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}