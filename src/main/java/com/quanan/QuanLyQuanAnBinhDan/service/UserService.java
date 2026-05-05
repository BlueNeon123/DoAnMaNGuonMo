package com.quanan.QuanLyQuanAnBinhDan.service;

import com.quanan.QuanLyQuanAnBinhDan.entity.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Integer id);
    void saveUser(User user); // Dùng 1 hàm save cho cả Thêm và Sửa
    void deleteUser(Integer id);
}