package com.quanan.QuanLyQuanAnBinhDan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories") // Tên bảng trong CSDL
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
    @Column(name = "id")
    private Integer id;

    
    @Column(name = "name", columnDefinition = "varchar(255)") // Thêm dòng này
    private String name;

    // --- BẮT BUỘC: Thêm constructor rỗng ---
    public Category() {
    }

    // --- Thêm Getters và Setters ---
    // (Chuột phải -> Source -> Generate Getters and Setters... -> Select All)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}