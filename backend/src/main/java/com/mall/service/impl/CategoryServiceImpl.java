package com.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.entity.Category;
import com.mall.mapper.CategoryMapper;
import com.mall.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<Category> getAllCategories() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getId);
        return this.list(wrapper);
    }

    @Override
    public Category createCategory(String name, String icon) {
        Category category = new Category();
        category.setName(name);
        category.setIcon(icon);
        this.save(category);
        return category;
    }

    @Override
    public Category updateCategory(Long id, String name, String icon) {
        Category category = this.getById(id);
        if (category == null) {
            throw new RuntimeException("分类不存在");
        }
        if (name != null) category.setName(name);
        if (icon != null) category.setIcon(icon);
        this.updateById(category);
        return category;
    }

    @Override
    public void deleteCategory(Long id) {
        this.removeById(id);
    }
}
