package com.quanan.QuanLyQuanAnBinhDan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quanan.QuanLyQuanAnBinhDan.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Để trống
	User findByUsername(String username);
	
}