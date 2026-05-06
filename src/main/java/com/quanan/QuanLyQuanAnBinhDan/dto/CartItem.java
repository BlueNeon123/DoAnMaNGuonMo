package com.quanan.QuanLyQuanAnBinhDan.dto;

import java.math.BigDecimal;

public class CartItem {
    private Integer dishId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    public CartItem(Integer dishId, String name, BigDecimal price, Integer quantity) {
        this.dishId = dishId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Integer getDishId() { return dishId; }
    public void setDishId(Integer dishId) { this.dishId = dishId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    // Tính thành tiền của món này
    public BigDecimal getSubTotal() {
        return price.multiply(new BigDecimal(quantity));
    }
}