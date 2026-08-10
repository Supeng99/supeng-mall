package com.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<Category> getAllCategories();
    Category createCategory(String name, String icon);
    Category updateCategory(Long id, String name, String icon);
    void deleteCategory(Long id);
}
