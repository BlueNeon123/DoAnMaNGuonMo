package com.quanan.QuanLyQuanAnBinhDan.service;

import com.quanan.QuanLyQuanAnBinhDan.entity.Customer;

public interface CustomerService {

    /**
     * Tìm khách hàng theo username
     * (Dùng để kiểm tra xem tài khoản đã tồn tại chưa)
     */
    Customer findByUsername(String username);

    /**
     * Lưu khách hàng mới (Đăng ký)
     * (Hàm này sẽ bao gồm mã hóa mật khẩu)
     */
    Customer save(Customer customer);
}