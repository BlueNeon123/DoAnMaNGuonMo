package com.quanan.QuanLyQuanAnBinhDan.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List; // Import List
import jakarta.persistence.*;

@Entity
@Table(name = "orders") // Tên bảng là 'orders'
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "order_time")
    private LocalDateTime orderTime; // Dùng LocalDateTime cho ngày giờ

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "status")
    private String status; // "UNPAID" hoặc "PAID"

    // --- Quan hệ N-1: Nhiều Order thuộc 1 Bàn ---
    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableFood tableFood; // Liên kết tới lớp TableFood

    // --- Quan hệ N-1: Nhiều Order do 1 User tạo ---
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Liên kết tới lớp User
    
    // --- Quan hệ 1-N: 1 Order có nhiều OrderDetail ---
    // 'mappedBy = "order"': Chỉ ra rằng 'order' là tên trường 
    // bên lớp OrderDetail quản lý quan hệ này.
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    public Order() {
    }

    // --- Generate Getters and Setters ---
    // (Chuột phải -> Source -> Generate Getters and Setters... -> Select All)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TableFood getTableFood() {
        return tableFood;
    }

    public void setTableFood(TableFood tableFood) {
        this.tableFood = tableFood;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
