package com.quanan.QuanLyQuanAnBinhDan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quanan.QuanLyQuanAnBinhDan.entity.Category;
import com.quanan.QuanLyQuanAnBinhDan.entity.Dish;
import com.quanan.QuanLyQuanAnBinhDan.service.CategoryService;
import com.quanan.QuanLyQuanAnBinhDan.service.DishService;

@Controller
public class PublicController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * Hiển thị trang Thực đơn Công khai
     */
    /**
     * Hiển thị trang Thực đơn Công khai (Kèm chức năng tìm kiếm)
     */
    @GetMapping("/")
    public String showPublicMenu(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        // 1. Lấy danh sách danh mục
        List<Category> categories = categoryService.getAllCategories();
        
        // 2. Xử lý tìm kiếm món ăn
        List<Dish> dishes;
        if (keyword != null && !keyword.isEmpty()) {
            // Nếu có từ khóa -> Tìm kiếm
            dishes = dishService.searchDishes(keyword);
        } else {
            // Nếu không -> Lấy tất cả
            dishes = dishService.getAllDishes();
        }

        // 3. Gửi dữ liệu ra view
        model.addAttribute("categories", categories);
        model.addAttribute("dishes", dishes);
        model.addAttribute("keyword", keyword); // Gửi lại từ khóa để hiển thị trong ô input
        
        return "public_menu";
    }
}