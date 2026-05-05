package com.quanan.QuanLyQuanAnBinhDan.controller;

import com.quanan.QuanLyQuanAnBinhDan.entity.TableFood;
import com.quanan.QuanLyQuanAnBinhDan.service.TableFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TableFoodController {

    @Autowired
    private TableFoodService tableFoodService;

    // 1. Hiển thị danh sách Bàn
    @GetMapping("/tables")
    public String listTables(Model model) {
        model.addAttribute("tables", tableFoodService.getAllTables());
        // Trả về file tables_list.html
        return "tables_list"; 
    }

    // 2. Hiển thị form Thêm mới
    @GetMapping("/tables/new")
    public String showCreateTableForm(Model model) {
        model.addAttribute("table", new TableFood());
        // Trả về file table_form.html
        return "table_form";
    }

    // 3. Hiển thị form Sửa
    @GetMapping("/tables/edit/{id}")
    public String showEditTableForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("table", tableFoodService.getTableById(id));
        return "table_form";
    }

    // 4. Lưu (Thêm mới hoặc Sửa)
    @PostMapping("/tables/save")
    public String saveTable(@ModelAttribute("table") TableFood table) {
        tableFoodService.saveTable(table);
        return "redirect:/tables";
    }

    // 5. Xóa
    @GetMapping("/tables/delete/{id}")
    public String deleteTable(@PathVariable("id") Integer id) {
        // Cần kiểm tra xem bàn có đang được dùng không...
        // Tạm thời cứ cho xóa
        tableFoodService.deleteTable(id);
        return "redirect:/tables";
    }
}