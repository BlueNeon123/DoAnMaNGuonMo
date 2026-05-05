package com.quanan.QuanLyQuanAnBinhDan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers") // Tên bảng mới là 'customers'
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username; // Tên đăng nhập (phải là duy nhất)

    @Column(name = "password", nullable = false)
    private String password; // Mật khẩu (sẽ được mã hóa)

    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "email", unique = true)
    private String email; // Email (nên là duy nhất)

    @Column(name = "phone")
    private String phone; // Số điện thoại

    // Constructor rỗng (bắt buộc)
    public Customer() {
    }

    // --- Generate Getters and Setters ---
    // (Chuột phải -> Source -> Generate Getters and Setters... -> Select All)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}