package com.quanan.QuanLyQuanAnBinhDan.service;

import com.quanan.QuanLyQuanAnBinhDan.entity.User;
import com.quanan.QuanLyQuanAnBinhDan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Import "máy mã hóa"
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // TIÊM "MÁY MÃ HÓA" VÀO ĐÂY
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        // Lấy mật khẩu thô (raw password) mà người dùng nhập từ form
        String rawPassword = user.getPassword();

        // Mã hóa mật khẩu đó
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Đặt lại mật khẩu đã mã hóa cho user
        user.setPassword(encodedPassword);

        // Lưu user (với mật khẩu đã mã hóa) vào CSDL
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}