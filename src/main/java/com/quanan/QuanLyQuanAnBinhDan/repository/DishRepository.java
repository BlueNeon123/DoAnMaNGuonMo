package com.quanan.QuanLyQuanAnBinhDan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quanan.QuanLyQuanAnBinhDan.entity.Dish;

// JpaRepository<Tên Entity, Kiểu dữ liệu của Khóa chính>
public interface DishRepository extends JpaRepository<Dish, Integer> {
    // Để trống, Spring Data JPA tự lo
	List<Dish> findByNameContaining(String keyword);
	}
