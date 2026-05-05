package com.quanan.QuanLyQuanAnBinhDan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.quanan.QuanLyQuanAnBinhDan.entity.Category;
import com.quanan.QuanLyQuanAnBinhDan.service.CategoryService;

import org.springframework.ui.Model;

@Controller // Đánh dấu đây là Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 1. Hiện thị danh sách tất cả Category
    @GetMapping("/categories")
    public String listCategories(Model model) {
        // Gửi một biến 'categories' chứa List<Category>
        model.addAttribute("categories", categoryService.getAllCategories());
        // Trả về file 'categories.html'
        return "categories"; 
    }

    // 2. Hiện thị form để Thêm mới
    @GetMapping("/categories/new")
    public String showCreateForm(Model model) {
        // Tạo một đối tượng Category rỗng
        Category category = new Category();
        model.addAttribute("category", category);
        // Trả về file 'category_form.html'
        return "category_form";
    }

    // 3. Lưu (Thêm mới hoặc Cập nhật)
    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute("category") Category category) {
        // @ModelAttribute lấy đối tượng 'category' từ form
        categoryService.saveCategory(category);
        // Chuyển hướng về trang danh sách
        return "redirect:/categories"; 
    }

    // 4. Hiện thị form để Cập nhật (Edit)
    @GetMapping("/categories/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        // @PathVariable lấy id từ đường dẫn
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        // Dùng chung file 'category_form.html'
        return "category_form";
    }

    // 5. Xóa
    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
