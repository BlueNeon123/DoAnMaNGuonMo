package com.quanan.QuanLyQuanAnBinhDan.service;

import java.util.List;

import com.quanan.QuanLyQuanAnBinhDan.entity.Category;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Integer id);
    Category saveCategory(Category category);
    void deleteCategory(Integer id);
}
