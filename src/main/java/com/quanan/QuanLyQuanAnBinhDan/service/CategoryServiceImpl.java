package com.quanan.QuanLyQuanAnBinhDan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quanan.QuanLyQuanAnBinhDan.entity.Category;
import com.quanan.QuanLyQuanAnBinhDan.repository.CategoryRepository;

@Service // Đánh dấu đây là một Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired // Tự động "tiêm" CategoryRepository vào
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Integer id) {
        // findById trả về Optional, .get() để lấy giá trị
        return categoryRepository.findById(id).get(); 
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
