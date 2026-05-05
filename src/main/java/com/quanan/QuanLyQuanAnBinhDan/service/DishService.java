package com.quanan.QuanLyQuanAnBinhDan.service;

import java.util.List;
import com.quanan.QuanLyQuanAnBinhDan.entity.Dish;

public interface DishService {
    List<Dish> getAllDishes();
    Dish saveDish(Dish dish);
    Dish getDishById(Integer id);
    void deleteDish(Integer id);
    List<Dish> searchDishes(String keyword);
}