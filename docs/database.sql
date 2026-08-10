-- =========================================
-- Mall E-commerce Database Schema
-- =========================================

-- Create database
CREATE DATABASE IF NOT EXISTS mall DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mall;

-- =========================================
-- User Table
-- =========================================
DROP TABLE IF EXISTS mall_user;
CREATE TABLE mall_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'User ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT 'Username',
    password VARCHAR(100) NOT NULL COMMENT 'Password (BCrypt encrypted)',
    nickname VARCHAR(50) COMMENT 'Nickname',
    email VARCHAR(100) COMMENT 'Email',
    phone VARCHAR(20) COMMENT 'Phone number',
    avatar VARCHAR(255) COMMENT 'Avatar URL',
    gender INT DEFAULT 0 COMMENT 'Gender: 0-Unknown, 1-Male, 2-Female',
    status INT DEFAULT 1 COMMENT 'Status: 0-Disabled, 1-Normal',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    INDEX idx_username (username),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='User table';

-- =========================================
-- Category Table
-- =========================================
DROP TABLE IF EXISTS mall_category;
CREATE TABLE mall_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Category ID',
    name VARCHAR(50) NOT NULL COMMENT 'Category name',
    parent_id BIGINT DEFAULT 0 COMMENT 'Parent category ID, 0 for root',
    sort INT DEFAULT 0 COMMENT 'Sort order',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    INDEX idx_parent_id (parent_id),
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Category table';

-- =========================================
-- Product Table
-- =========================================
DROP TABLE IF EXISTS mall_product;
CREATE TABLE mall_product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Product ID',
    name VARCHAR(100) NOT NULL COMMENT 'Product name',
    subtitle VARCHAR(200) COMMENT 'Product subtitle',
    category_id BIGINT COMMENT 'Category ID',
    main_image VARCHAR(255) COMMENT 'Main image URL',
    sub_images TEXT COMMENT 'Sub-images (JSON array)',
    detail TEXT COMMENT 'Product detail (HTML)',
    price DECIMAL(10,2) NOT NULL COMMENT 'Price',
    stock INT DEFAULT 0 COMMENT 'Stock quantity',
    status INT DEFAULT 1 COMMENT 'Status: 0-Off shelf, 1-On shelf',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    INDEX idx_category_id (category_id),
    INDEX idx_status (status),
    INDEX idx_name (name),
    FULLTEXT INDEX ft_name_subtitle (name, subtitle)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Product table';

-- =========================================
-- Order Table
-- =========================================
DROP TABLE IF EXISTS mall_order;
CREATE TABLE mall_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Order ID',
    order_no VARCHAR(32) NOT NULL UNIQUE COMMENT 'Order number',
    user_id BIGINT NOT NULL COMMENT 'User ID',
    total_price DECIMAL(10,2) NOT NULL COMMENT 'Total price',
    pay_status INT DEFAULT 0 COMMENT 'Payment status: 0-Unpaid, 1-Paid',
    pay_type INT COMMENT 'Payment type: 1-WeChat, 2-Alipay, 3-Bank card',
    pay_time DATETIME COMMENT 'Payment time',
    order_status INT DEFAULT 0 COMMENT 'Order status: 0-Pending, 1-Paid, 2-Shipped, 3-Completed, 4-Cancelled',
    shipping_address VARCHAR(255) COMMENT 'Shipping address',
    receiver_name VARCHAR(50) COMMENT 'Receiver name',
    receiver_phone VARCHAR(20) COMMENT 'Receiver phone',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    INDEX idx_order_no (order_no),
    INDEX idx_user_id (user_id),
    INDEX idx_order_status (order_status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Order table';

-- =========================================
-- Order Item Table
-- =========================================
DROP TABLE IF EXISTS mall_order_item;
CREATE TABLE mall_order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Order item ID',
    order_id BIGINT NOT NULL COMMENT 'Order ID',
    product_id BIGINT NOT NULL COMMENT 'Product ID',
    product_name VARCHAR(100) NOT NULL COMMENT 'Product name (snapshot)',
    product_image VARCHAR(255) COMMENT 'Product image (snapshot)',
    price DECIMAL(10,2) NOT NULL COMMENT 'Unit price (snapshot)',
    quantity INT NOT NULL COMMENT 'Quantity',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Order item table';

-- =========================================
-- Shopping Cart Table
-- =========================================
DROP TABLE IF EXISTS mall_cart;
CREATE TABLE mall_cart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Cart ID',
    user_id BIGINT NOT NULL COMMENT 'User ID',
    product_id BIGINT NOT NULL COMMENT 'Product ID',
    quantity INT NOT NULL DEFAULT 1 COMMENT 'Quantity',
    checked INT DEFAULT 1 COMMENT 'Selected: 0-No, 1-Yes',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
    INDEX idx_user_id (user_id),
    INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Shopping cart table';

-- =========================================
-- Insert Sample Data
-- =========================================

-- Insert admin user (password: admin123)
-- NOTE: The password hash below is a placeholder. Use the /api/user/register
-- endpoint to create the real admin account after the backend starts:
--   POST http://localhost:8080/api/user/register?username=admin&password=admin123
INSERT INTO mall_user (username, password, nickname, email, status) VALUES
('admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 'Administrator', 'admin@mall.com', 1),
('test', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 'Test User', 'test@mall.com', 1);

-- Insert categories
INSERT INTO mall_category (name, parent_id, sort) VALUES
('Electronics', 0, 1),
('Clothing', 0, 2),
('Books', 0, 3),
('Smartphones', 1, 1),
('Laptops', 1, 2),
('Accessories', 1, 3),
('Men', 2, 1),
('Women', 2, 2);

-- Insert products
INSERT INTO mall_product (name, subtitle, category_id, price, stock, status) VALUES
('iPhone 15 Pro', 'Latest Apple flagship phone with A17 Pro chip', 4, 8999.00, 100, 1),
('MacBook Pro 14', 'M3 Pro chip, 18-hour battery life', 5, 16999.00, 50, 1),
('AirPods Pro 2', 'Active noise cancellation, spatial audio', 6, 1899.00, 200, 1),
('iPad Pro 12.9', 'M2 chip, Liquid Retina XDR display', 1, 9299.00, 80, 1),
('Apple Watch Ultra 2', 'Adventure-ready smartwatch', 6, 5999.00, 60, 1),
('Samsung Galaxy S24 Ultra', 'AI-powered smartphone', 4, 9699.00, 90, 1),
('Dell XPS 15', 'Intel Core i9, OLED display', 5, 14999.00, 40, 1),
('Nike Air Max', 'Comfortable running shoes', 7, 899.00, 150, 1);

-- Insert sample orders
INSERT INTO mall_order (order_no, user_id, total_price, pay_status, order_status, receiver_name, receiver_phone, shipping_address) VALUES
('ORD2024010100001', 2, 8999.00, 1, 3, 'Zhang San', '13800138000', 'Beijing, Chaoyang District'),
('ORD2024010100002', 2, 3780.00, 1, 2, 'Zhang San', '13800138000', 'Beijing, Chaoyang District'),
('ORD2024010200001', 2, 16999.00, 1, 1, 'Li Si', '13900139000', 'Shanghai, Pudong District');
