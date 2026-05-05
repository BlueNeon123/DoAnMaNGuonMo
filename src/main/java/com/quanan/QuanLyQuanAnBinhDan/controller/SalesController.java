package com.quanan.QuanLyQuanAnBinhDan.controller;

import com.quanan.QuanLyQuanAnBinhDan.entity.Dish;
import com.quanan.QuanLyQuanAnBinhDan.entity.Order;
import com.quanan.QuanLyQuanAnBinhDan.entity.TableFood;
import com.quanan.QuanLyQuanAnBinhDan.service.DishService;
import com.quanan.QuanLyQuanAnBinhDan.service.OrderService;
import com.quanan.QuanLyQuanAnBinhDan.service.TableFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SalesController {

    @Autowired
    private TableFoodService tableFoodService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishService dishService;

    /**
     * 1. Hiển thị trang Sơ đồ bàn (Trang bán hàng chính)
     */
    @GetMapping("/sales")
    public String showSalesMap(Model model) {
        List<TableFood> tables = tableFoodService.getAllTables();
        model.addAttribute("tables", tables);
        return "sales_map";
    }

    /**
     * 2. Khi bấm vào một bàn: Hiển thị trang Chi tiết Order
     */
    @GetMapping("/sales/table/{tableId}")
    public String showOrderForTable(@PathVariable("tableId") Integer tableId, Model model) {
        TableFood table = tableFoodService.getTableById(tableId);
        Order order = orderService.getOrCreateUnpaidOrderForTable(table);
        List<Dish> menu = dishService.getAllDishes();

        model.addAttribute("table", table);
        model.addAttribute("order", order);
        model.addAttribute("menu", menu);
        
        return "order_detail";
    }

    /**
     * 3. Thêm món ăn vào Order
     */
    @PostMapping("/sales/order/addDish")
    public String addDishToOrder(@RequestParam("orderId") Integer orderId,
                                 @RequestParam("dishId") Integer dishId,
                                 @RequestParam("quantity") Integer quantity) {
        
        Order order = orderService.getOrderDetails(orderId);
        orderService.addDishToOrder(order, dishId, quantity);
        
        // Quay lại trang chi tiết order của bàn đó
        return "redirect:/sales/table/" + order.getTableFood().getId();
    }
    
    /**
     * 4. Xóa một món (OrderDetail) khỏi Order (ĐÃ SỬA)
     */
    @GetMapping("/sales/order/removeDish/{orderDetailId}")
    public String removeDishFromOrder(@PathVariable("orderDetailId") Integer orderDetailId) {
        
        // Gọi hàm service (hàm này đã được sửa để trả về Order)
        Order order = orderService.removeDishFromOrder(orderDetailId);
        
        if (order != null) {
            // Quay lại đúng bàn
            return "redirect:/sales/table/" + order.getTableFood().getId();
        } else {
            // Nếu lỗi, quay về sơ đồ bàn
            return "redirect:/sales";
        }
    }
    

    /**
     * 5. Thanh toán Order
     */
    @PostMapping("/sales/order/pay")
    public String payOrder(@RequestParam("orderId") Integer orderId) {
        
        orderService.payOrder(orderId);
        
        // Thanh toán xong, quay về Sơ đồ bàn
        return "redirect:/sales";
    }
}