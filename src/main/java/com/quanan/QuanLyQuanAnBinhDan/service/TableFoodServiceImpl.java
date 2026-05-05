package com.quanan.QuanLyQuanAnBinhDan.service;

import com.quanan.QuanLyQuanAnBinhDan.entity.TableFood;
import com.quanan.QuanLyQuanAnBinhDan.repository.TableFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableFoodServiceImpl implements TableFoodService {

    @Autowired
    private TableFoodRepository tableFoodRepository;

    @Override
    public List<TableFood> getAllTables() {
        return tableFoodRepository.findAll();
    }

    @Override
    public TableFood getTableById(Integer id) {
        return tableFoodRepository.findById(id).orElse(null);
    }

    // THÊM 2 HÀM MỚI NÀY:
    @Override
    public TableFood saveTable(TableFood tableFood) {
        // Khi lưu, nếu trạng thái là null (do form),
        // mặc định là 'EMPTY'
        if (tableFood.getStatus() == null) {
            tableFood.setStatus("EMPTY");
        }
        return tableFoodRepository.save(tableFood);
    }

    @Override
    public void deleteTable(Integer id) {
        tableFoodRepository.deleteById(id);
    }
}