package com.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.Result;
import com.mall.entity.Order;
import com.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public Result<Page<Order>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return Result.success(orderService.getOrderPage(pageNum, pageSize, keyword));
    }

    @PutMapping("/status")
    public Result<Void> updateStatus(@RequestParam Long id, @RequestParam Integer orderStatus) {
        orderService.updateOrderStatus(id, orderStatus);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result<Order> detail(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }
}
