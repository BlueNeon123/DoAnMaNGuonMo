package com.quanan.QuanLyQuanAnBinhDan.service;

import java.util.List;


import com.quanan.QuanLyQuanAnBinhDan.dto.RevenueReportDTO;
import com.quanan.QuanLyQuanAnBinhDan.entity.Order;
import com.quanan.QuanLyQuanAnBinhDan.entity.TableFood;

public interface OrderService {
    
    /**
     * Tìm order CHƯA THANH TOÁN (UNPAID) của một bàn.
     * Nếu không có, tạo một order mới.
     */
    Order getOrCreateUnpaidOrderForTable(TableFood table);

    /**
     * Thêm một món ăn (dish) vào một order.
     */
    void addDishToOrder(Order order, Integer dishId, Integer quantity);

    /**
     * Tính toán lại tổng tiền của một order.
     */
    void calculateOrderTotal(Order order);

    /**
     * Lấy chi tiết một order (bao gồm cả các món đã gọi).
     */
    Order getOrderDetails(Integer orderId);
    
    /**
     * Xóa một món ăn (OrderDetail) khỏi Order
     */
    Order removeDishFromOrder(Integer orderDetailId);

    /**
     * Xử lý thanh toán.
     */
    void payOrder(Integer orderId);
    List<RevenueReportDTO> getDailyRevenueReport();
}