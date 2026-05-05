package com.quanan.QuanLyQuanAnBinhDan.service;

import com.quanan.QuanLyQuanAnBinhDan.entity.Customer;
import com.quanan.QuanLyQuanAnBinhDan.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * Service này CHỈ DÀNH RIÊNG cho việc Đăng nhập của Khách hàng.
 */
@Service
@Primary
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 1. Tìm khách hàng (Customer) trong CSDL bằng username
        Customer customer = customerRepository.findByUsername(username);
        
        if (customer == null) {
            // 2. Nếu không tìm thấy, ném lỗi
            throw new UsernameNotFoundException("Không tìm thấy khách hàng: " + username);
        }

        // 3. Nếu tìm thấy, chuyển đổi Customer thành UserDetails của Spring
        // Chúng ta gán cho họ một vai trò (ROLE) cố định là "CUSTOMER"
        return new org.springframework.security.core.userdetails.User(
                customer.getUsername(),
                customer.getPassword(), // Mật khẩu đã mã hóa
                getAuthorities("CUSTOMER") // Gán vai trò là CUSTOMER
        );
    }

    // Hàm này chuyển đổi Role (String) thành Quyền (GrantedAuthority)
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }
}