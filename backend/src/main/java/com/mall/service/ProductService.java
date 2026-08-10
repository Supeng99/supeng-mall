package com.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.entity.Product;

import java.math.BigDecimal;

public interface ProductService extends IService<Product> {
    Page<Product> getProductPage(Integer pageNum, Integer pageSize, String keyword, Long categoryId);
    Product createProduct(String name, String subtitle, Long categoryId, BigDecimal price, Integer stock);
    Product updateProduct(Long id, String name, String subtitle, Long categoryId, BigDecimal price, Integer stock);
}
