package com.quanan.QuanLyQuanAnBinhDan.repository;

import com.quanan.QuanLyQuanAnBinhDan.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * Tự động tìm khách hàng bằng 'username'.
     * Dùng để kiểm tra khi đăng ký/đăng nhập.
     */
    Customer findByUsername(String username);

}