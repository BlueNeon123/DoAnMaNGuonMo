package com.quanan.QuanLyQuanAnBinhDan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quanan.QuanLyQuanAnBinhDan.dto.RevenueReportDTO;
import com.quanan.QuanLyQuanAnBinhDan.entity.Customer;
import com.quanan.QuanLyQuanAnBinhDan.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query("SELECT new com.quanan.QuanLyQuanAnBinhDan.dto.RevenueReportDTO(CAST(o.orderTime AS LocalDate), SUM(o.totalAmount)) " +
	           "FROM Order o " +
	           "WHERE o.status = 'PAID' " +
	           "GROUP BY CAST(o.orderTime AS LocalDate) " +
	           "ORDER BY CAST(o.orderTime AS LocalDate) DESC")
	    List<RevenueReportDTO> getDailyRevenueReport();
    
	List<Order> findByCustomerIsNotNullOrderByOrderTimeDesc();
	List<Order> findByCustomerOrderByOrderTimeDesc(Customer customer);
}