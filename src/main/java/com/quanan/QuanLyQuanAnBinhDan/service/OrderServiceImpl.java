package com.quanan.QuanLyQuanAnBinhDan.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quanan.QuanLyQuanAnBinhDan.dto.RevenueReportDTO;
import com.quanan.QuanLyQuanAnBinhDan.entity.Dish;
import com.quanan.QuanLyQuanAnBinhDan.entity.Order;
import com.quanan.QuanLyQuanAnBinhDan.entity.OrderDetail;
import com.quanan.QuanLyQuanAnBinhDan.entity.TableFood;
import com.quanan.QuanLyQuanAnBinhDan.repository.DishRepository;
import com.quanan.QuanLyQuanAnBinhDan.repository.OrderDetailRepository;
import com.quanan.QuanLyQuanAnBinhDan.repository.OrderRepository;
import com.quanan.QuanLyQuanAnBinhDan.repository.TableFoodRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private TableFoodRepository tableFoodRepository;

    @Autowired
    private DishRepository dishRepository;
    
    // @Transactional đảm bảo tất cả các thao tác CSDL 
    // trong hàm này cùng thành công hoặc cùng thất bại.
    @Override
    @Transactional
    public Order getOrCreateUnpaidOrderForTable(TableFood table) {
        // 1. Tìm trong CSDL xem có order nào "UNPAID" của bàn này không
        Optional<Order> existingOrder = orderRepository.findAll().stream()
                .filter(o -> o.getTableFood().getId().equals(table.getId()) && "UNPAID".equals(o.getStatus()))
                .findFirst();

        if (existingOrder.isPresent()) {
            // 2. Nếu có, trả về order đó
            return existingOrder.get();
        } else {
            // 3. Nếu không, tạo một order mới
            Order newOrder = new Order();
            newOrder.setTableFood(table);
            newOrder.setStatus("UNPAID"); // Trạng thái chưa thanh toán
            newOrder.setOrderTime(LocalDateTime.now());
            newOrder.setTotalAmount(BigDecimal.ZERO); // Tổng tiền ban đầu = 0
            
            // 4. Đổi trạng thái bàn thành "OCCUPIED" (Có khách)
            table.setStatus("OCCUPIED");
            tableFoodRepository.save(table);

            return orderRepository.save(newOrder);
        }
    }

    @Override
    @Transactional
    public void addDishToOrder(Order order, Integer dishId, Integer quantity) {
        // 1. Lấy thông tin món ăn từ CSDL
        Dish dish = dishRepository.findById(dishId).orElse(null);
        if (dish == null) {
            // Xử lý lỗi nếu món ăn không tồn tại
            return;
        }

        // 2. Tạo một chi tiết đơn hàng (OrderDetail) mới
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setDish(dish);
        orderDetail.setQuantity(quantity);
        orderDetail.setPriceAtOrder(dish.getPrice()); // Lưu lại giá tại thời điểm đặt

        // 3. Lưu chi tiết này vào CSDL
        orderDetailRepository.save(orderDetail);

        // 4. Cập nhật lại tổng tiền cho Order chính
        calculateOrderTotal(order);
    }

    @Override
    @Transactional
    public void calculateOrderTotal(Order order) {
        // 1. Lấy lại tất cả chi tiết (OrderDetail) của Order này
        // (Chúng ta cần lấy lại từ CSDL để đảm bảo dữ liệu mới nhất)
        Order orderWithDetails = orderRepository.findById(order.getId()).get();
        
        BigDecimal total = BigDecimal.ZERO;
        
        // 2. Cộng tiền của từng món
        for (OrderDetail detail : orderWithDetails.getOrderDetails()) {
            BigDecimal lineTotal = detail.getPriceAtOrder().multiply(new BigDecimal(detail.getQuantity()));
            total = total.add(lineTotal);
        }
        
        // 3. Cập nhật tổng tiền mới
        orderWithDetails.setTotalAmount(total);
        orderRepository.save(orderWithDetails);
    }
    
    @Override
    public Order getOrderDetails(Integer orderId) {
        // .get() để lấy order, nếu không có sẽ ném lỗi
        return orderRepository.findById(orderId).get();
    }
    
 // HÀM MỚI ĐÃ SỬA:
    @Override
    @Transactional
    public Order removeDishFromOrder(Integer orderDetailId) {
        // 1. Tìm OrderDetail theo ID
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElse(null);

        if (orderDetail != null) {
            Order order = orderDetail.getOrder();

            // 2. Xóa nó khỏi CSDL
            orderDetailRepository.delete(orderDetail);

            // 3. Tính toán lại tổng tiền
            calculateOrderTotal(order);

            // 4. Trả về Order để Controller biết đường redirect
            return order;
        }

        // 5. Nếu không tìm thấy, trả về null
        return null;
    }
    
    @Override
    @Transactional
    public void payOrder(Integer orderId) {
        // 1. Lấy order
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return;
        }
        
        // 2. Đổi trạng thái Order thành "PAID"
        order.setStatus("PAID");
        orderRepository.save(order);
        
        // 3. Đổi trạng thái Bàn về "EMPTY"
        TableFood table = order.getTableFood();
        table.setStatus("EMPTY");
        tableFoodRepository.save(table);
    }
    @Override
    public List<RevenueReportDTO> getDailyRevenueReport() {
        // Chỉ cần gọi hàm mới của Repository
        return orderRepository.getDailyRevenueReport();
    }
}