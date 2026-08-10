package com.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.entity.Order;

public interface OrderService extends IService<Order> {
    Page<Order> getOrderPage(Integer pageNum, Integer pageSize, String keyword);
    void updateOrderStatus(Long id, Integer orderStatus);
}
