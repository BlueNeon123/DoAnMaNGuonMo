package com.quanan.QuanLyQuanAnBinhDan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quanan.QuanLyQuanAnBinhDan.entity.Category;

// JpaRepository<Tên Entity, Kiểu dữ liệu của Khóa chính>
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Spring Data JPA sẽ tự động cung cấp các hàm:
    // save(), findById(), findAll(), deleteById(), ...
    // Chúng ta không cần viết code gì thêm!
}
