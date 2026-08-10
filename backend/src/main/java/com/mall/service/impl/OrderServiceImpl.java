package com.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.entity.Order;
import com.mall.mapper.OrderMapper;
import com.mall.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public Page<Order> getOrderPage(Integer pageNum, Integer pageSize, String keyword) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Order::getOrderNo, keyword)
                   .or()
                   .like(Order::getReceiverName, keyword);
        }
        wrapper.orderByDesc(Order::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public void updateOrderStatus(Long id, Integer orderStatus) {
        Order order = this.getById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        order.setOrderStatus(orderStatus);
        this.updateById(order);
    }
}
