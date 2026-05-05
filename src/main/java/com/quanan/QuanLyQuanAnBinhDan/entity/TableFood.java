package com.quanan.QuanLyQuanAnBinhDan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tables") // Giả sử tên bảng là 'tables'
public class TableFood { // Đổi tên lớp thành TableFood

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(50)")
    private String name;

    @Column(name = "status")
    private String status; // ('EMPTY', 'OCCUPIED')

    public TableFood() {
    }

    // --- Generate Getters and Setters ---

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
