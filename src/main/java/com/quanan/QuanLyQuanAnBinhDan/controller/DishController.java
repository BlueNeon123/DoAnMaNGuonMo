package com.quanan.QuanLyQuanAnBinhDan.controller;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

// Import các file Entity và Service với TÊN PACKAGE ĐÚNG
import com.quanan.QuanLyQuanAnBinhDan.entity.Category;
import com.quanan.QuanLyQuanAnBinhDan.entity.Dish;
import com.quanan.QuanLyQuanAnBinhDan.service.CategoryService;
import com.quanan.QuanLyQuanAnBinhDan.service.DishService;


@Controller
public class DishController {

    // Tiêm (inject) service món ăn
    @Autowired
    private DishService dishService;

    // Tiêm (inject) cả service danh mục
    @Autowired
    private CategoryService categoryService; 

 // 1. Hiện thị danh sách Món ăn (ĐÃ SỬA ĐỂ TÌM KIẾM)
    @GetMapping("/dishes")
    public String listDishes(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Dish> dishes;
        
        if (keyword != null && !keyword.isEmpty()) {
            // Nếu có từ khóa -> Tìm kiếm
            dishes = dishService.searchDishes(keyword);
        } else {
            // Nếu không -> Lấy hết
            dishes = dishService.getAllDishes();
        }
        
        model.addAttribute("dishes", dishes);
        model.addAttribute("keyword", keyword); // Gửi lại từ khóa ra giao diện để giữ trong ô input
        return "dishes";
    }
    /**
     * 2. Hiện thị form để Thêm mới Món ăn
     */
    @GetMapping("/dishes/new")
    public String showCreateForm(Model model) {
        // Lấy tất cả danh mục (để làm ô chọn <select>)
        List<Category> categories = categoryService.getAllCategories();
        
        // Tạo một đối tượng Dish rỗng
        model.addAttribute("dish", new Dish()); 
        // Gửi danh sách danh mục ra form
        model.addAttribute("categories", categories); 
        
        // Trả về file 'dish_form.html'
        return "dish_form"; 
    }

    /**
     * 3. Hiện thị form để Cập nhật (Edit) Món ăn
     */
    @GetMapping("/dishes/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        // 1. Lấy món ăn theo id
        Dish dish = dishService.getDishById(id);
        // 2. Lấy tất cả danh mục (để làm ô chọn <select>)
        List<Category> categories = categoryService.getAllCategories();
        
        // 3. Gửi cả hai ra form
        model.addAttribute("dish", dish);
        model.addAttribute("categories", categories);
        
        // Dùng chung file 'dish_form.html'
        return "dish_form";
    }

    /**
     * 4. Lưu (Thêm mới hoặc Cập nhật) - CÓ XỬ LÝ ẢNH
     */
    @PostMapping("/dishes/save")
    public String saveDish(@ModelAttribute("dish") Dish dish,
                           @RequestParam("imageFile") MultipartFile multipartFile) throws IOException {
        
        String fileName = "";

        // 1. Kiểm tra xem người dùng có upload ảnh mới không
        if (!multipartFile.isEmpty()) {
            fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
            dish.setImageUrl(fileName); // Lưu tên file vào CSDL
            
            // Lưu file xuống ổ cứng
            Dish savedDish = dishService.saveDish(dish); // Lưu trước để lấy ID (nếu cần)
            
            String uploadDir = "./dish-images/"; // Thư mục lưu ảnh
            Path uploadPath = Paths.get(uploadDir);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                // Ghi đè nếu file đã tồn tại
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Không thể lưu file: " + fileName, ioe);
            }
        } else {
            // Nếu không upload ảnh mới, kiểm tra xem có ảnh cũ không
            // (Trường hợp Edit: Cần giữ nguyên ảnh cũ nếu không chọn ảnh mới)
            if (dish.getId() != null) {
                Dish existingDish = dishService.getDishById(dish.getId());
                dish.setImageUrl(existingDish.getImageUrl());
            }
            dishService.saveDish(dish);
        }

        return "redirect:/dishes";
    }

    /**
     * 5. Xóa Món ăn
     */
    @GetMapping("/dishes/delete/{id}")
    public String deleteDish(@PathVariable("id") Integer id) {
        dishService.deleteDish(id);
        // Chuyển hướng về trang danh sách
        return "redirect:/dishes";
    }
}