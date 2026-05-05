package com.quanan.QuanLyQuanAnBinhDan.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Đây là một DTO (Data Transfer Object)
 * Nó KHÔNG phải là Entity, nó chỉ dùng để chứa kết quả
 * từ một câu truy vấn (Query) tùy chỉnh.
 */
public class RevenueReportDTO {

    private LocalDate date;
    private BigDecimal totalRevenue;

    /**
     * Constructor này RẤT QUAN TRỌNG.
     * JPQL (Câu @Query) sẽ dùng nó để tạo đối tượng.
     * Các tham số phải khớp với thứ tự trong câu SELECT.
     */
    public RevenueReportDTO(LocalDate date, BigDecimal totalRevenue) {
        this.date = date;
        this.totalRevenue = totalRevenue;
    }

    // Getters and Setters
    // (Chuột phải -> Source -> Generate Getters and Setters)
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}