package com.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("mall_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String orderNo;
    
    private Long userId;
    
    private BigDecimal totalPrice;
    
    private Integer payStatus;
    
    private Integer payType;
    
    private String payTime;
    
    private Integer orderStatus;
    
    private String shippingAddress;
    
    private String receiverName;
    
    private String receiverPhone;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}
