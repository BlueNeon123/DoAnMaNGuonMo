package com.quanan.QuanLyQuanAnBinhDan.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quanan.QuanLyQuanAnBinhDan.entity.Dish;
import com.quanan.QuanLyQuanAnBinhDan.repository.DishRepository;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    @Override
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    @Override
    public Dish saveDish(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public Dish getDishById(Integer id) {
        // .get() sẽ lấy Dish, nếu không tìm thấy sẽ ném lỗi
        // Tạm thời dùng .get() cho đơn giản
        return dishRepository.findById(id).get();
    }

    @Override
    public void deleteDish(Integer id) {
        dishRepository.deleteById(id);
    }
    @Override
    public List<Dish> searchDishes(String keyword) {
        return dishRepository.findByNameContaining(keyword);
    }
}