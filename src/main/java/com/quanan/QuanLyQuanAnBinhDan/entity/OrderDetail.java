package com.quanan.QuanLyQuanAnBinhDan.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price_at_order")
    private BigDecimal priceAtOrder; // Giá tại thời điểm đặt

    // --- Quan hệ N-1: Nhiều OrderDetail thuộc 1 Order ---
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // --- Quan hệ N-1: Nhiều OrderDetail trỏ đến 1 Dish ---
    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    public OrderDetail() {
    }

    // --- Generate Getters and Setters ---
    // (Chuột phải -> Source -> Generate Getters and Setters... -> Select All)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtOrder() {
        return priceAtOrder;
    }

    public void setPriceAtOrder(BigDecimal priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
