package com.quanan.QuanLyQuanAnBinhDan.service;

import com.quanan.QuanLyQuanAnBinhDan.entity.TableFood;
import java.util.List;

public interface TableFoodService {
    List<TableFood> getAllTables();
    TableFood getTableById(Integer id);
    
    // THÊM 2 DÒNG NÀY:
    TableFood saveTable(TableFood tableFood);
    void deleteTable(Integer id);
}