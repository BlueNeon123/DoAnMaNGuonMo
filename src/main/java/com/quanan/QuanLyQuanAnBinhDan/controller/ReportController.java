package com.quanan.QuanLyQuanAnBinhDan.controller;

import com.quanan.QuanLyQuanAnBinhDan.dto.RevenueReportDTO;
import com.quanan.QuanLyQuanAnBinhDan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private OrderService orderService; // Tiêm OrderService

    /**
     * Hiển thị trang Báo cáo Doanh thu hàng ngày
     */
    @GetMapping("/reports/daily-revenue")
    public String showDailyRevenueReport(Model model) {
        
        // 1. Gọi service để lấy dữ liệu báo cáo (List<RevenueReportDTO>)
        List<RevenueReportDTO> reportData = orderService.getDailyRevenueReport();
        
        // 2. Gửi dữ liệu ra file HTML
        model.addAttribute("reportData", reportData);
        
        // 3. Trả về file report_daily.html
        return "report_daily";
    }
}