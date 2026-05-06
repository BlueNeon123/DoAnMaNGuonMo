package com.quanan.QuanLyQuanAnBinhDan.config;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.quanan.QuanLyQuanAnBinhDan.service.CustomUserDetailsService;
import com.quanan.QuanLyQuanAnBinhDan.service.CustomerUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

 // Sửa lại để KHÔNG mã hóa mật khẩu nữa
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

    // === BEAN 1: KHÁCH HÀNG (CUSTOMER) ===
    @Bean
    @Order(1) 
    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity http) throws Exception {
        
        // Tạo bộ xác thực riêng cho Khách hàng
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return http
            .securityMatcher("/customer-login", "/register", "/customer-logout") 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/customer-login", "/register").permitAll() 
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/customer-login")         
                .loginProcessingUrl("/customer-login") 
                .defaultSuccessUrl("/", true)       
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/customer-logout", "GET")) 
                .logoutSuccessUrl("/")         
                .permitAll()
            )
            .authenticationProvider(authProvider) // <--- ĐÃ SỬA: Dùng authenticationProvider
            .build();
    }

    // === BEAN 2: QUẢN LÝ (ADMIN/STAFF) ===
    @Bean
    @Order(2)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        
        // Tạo bộ xác thực riêng cho Admin
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return http
            .securityMatcher("/admin/**", "/sales/**", "/categories/**", "/dishes/**", "/tables/**", "/users/**", "/reports/**", "/login", "/logout")
            .authorizeHttpRequests(auth -> auth 
                .requestMatchers("/admin", "/sales/**").authenticated() 
                .requestMatchers("/categories/**", "/dishes/**", "/tables/**", "/users/**", "/reports/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")               
                .loginProcessingUrl("/login")      
                .defaultSuccessUrl("/admin", true) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) 
                .logoutSuccessUrl("/login?logout") 
                .permitAll()
            )
            .authenticationProvider(authProvider) // <--- ĐÃ SỬA: Dùng authenticationProvider
            .build();
    }
    
    // === BEAN 3: PUBLIC ===
    @Bean
    @Order(3)
    public SecurityFilterChain publicSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .securityMatcher("/", "/menu", "/css/**", "/js/**", "/dish-images/**")
            .authorizeHttpRequests(auth -> auth 
                .requestMatchers("/**").permitAll() 
            )
            .build();
    }
}