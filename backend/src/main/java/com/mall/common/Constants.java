package com.mall.common;

public class Constants {
    public static final Integer STATUS_NORMAL = 1;
    public static final Integer STATUS_DISABLE = 0;
    
    public static final Integer ORDER_STATUS_PENDING = 0;
    public static final Integer ORDER_STATUS_PAID = 1;
    public static final Integer ORDER_STATUS_SHIPPED = 2;
    public static final Integer ORDER_STATUS_COMPLETED = 3;
    public static final Integer ORDER_STATUS_CANCELLED = 4;
    
    public static final String JWT_TOKEN_HEADER = "Authorization";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
}
