package com.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.entity.Product;
import com.mall.mapper.ProductMapper;
import com.mall.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public Page<Product> getProductPage(Integer pageNum, Integer pageSize, String keyword, Long categoryId) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Product::getName, keyword)
                   .or()
                   .like(Product::getSubtitle, keyword);
        }
        if (categoryId != null) {
            wrapper.eq(Product::getCategoryId, categoryId);
        }
        wrapper.eq(Product::getStatus, 1);
        wrapper.orderByDesc(Product::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public Product createProduct(String name, String subtitle, Long categoryId, BigDecimal price, Integer stock) {
        Product product = new Product();
        product.setName(name);
        product.setSubtitle(subtitle);
        product.setCategoryId(categoryId);
        product.setPrice(price);
        product.setStock(stock);
        product.setStatus(1);
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());
        this.save(product);
        return product;
    }

    @Override
    public Product updateProduct(Long id, String name, String subtitle, Long categoryId, BigDecimal price, Integer stock) {
        Product product = this.getById(id);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        if (name != null) product.setName(name);
        if (subtitle != null) product.setSubtitle(subtitle);
        if (categoryId != null) product.setCategoryId(categoryId);
        if (price != null) product.setPrice(price);
        if (stock != null) product.setStock(stock);
        product.setUpdateTime(LocalDateTime.now());
        this.updateById(product);
        return product;
    }
}
