package com.quanan.QuanLyQuanAnBinhDan.service;

import com.quanan.QuanLyQuanAnBinhDan.entity.User;
import com.quanan.QuanLyQuanAnBinhDan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Spring Security sẽ tự động tìm hàm này qua UserRepository
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 1. Tìm user trong CSDL
        User user = userRepository.findByUsername(username);
        
        if (user == null) {
            // 2. Nếu không tìm thấy, ném lỗi
            throw new UsernameNotFoundException("Không tìm thấy người dùng với username: " + username);
        }

        // 3. Chuyển đổi User của bạn thành UserDetails của Spring
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getRole())
        );
    }

    // Hàm chuyển đổi Role (String) thành Quyền (GrantedAuthority)
    private Collection<SimpleGrantedAuthority> getAuthorities(String role) {
        // Luôn phải thêm tiền tố "ROLE_"
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }
}