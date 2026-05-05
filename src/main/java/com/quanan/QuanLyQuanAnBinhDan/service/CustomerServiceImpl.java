package com.quanan.QuanLyQuanAnBinhDan.service;

import com.quanan.QuanLyQuanAnBinhDan.entity.Customer;
import com.quanan.QuanLyQuanAnBinhDan.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Import "máy mã hóa"
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Tiêm "máy mã hóa" mà chúng ta đã tạo trong SecurityConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer save(Customer customer) {
        // 1. Lấy mật khẩu thô (raw password) từ form
        String rawPassword = customer.getPassword();
        
        // 2. Mã hóa mật khẩu đó
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // 3. Đặt lại mật khẩu đã mã hóa cho customer
        customer.setPassword(encodedPassword);

        // 4. Lưu customer (với mật khẩu đã mã hóa) vào CSDL
        return customerRepository.save(customer);
    }
}