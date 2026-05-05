package com.quanan.QuanLyQuanAnBinhDan.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(255)")
    private String name;

    @Column(name = "price")
    private BigDecimal price; // Dùng BigDecimal cho tiền tệ

    @Column(name = "image_url")
    private String imageUrl;

    // --- Định nghĩa quan hệ N-1 với Category ---
    @ManyToOne
    @JoinColumn(name = "category_id") // Tên cột khóa ngoại
    private Category category;
    
    // --- Constructor rỗng ---
    public Dish() {
    }

    // --- Generate Getters and Setters ---
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
