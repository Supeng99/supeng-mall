# Mall 电商后台管理系统

> 前后端分离 Spring Boot + Vue 3 实战项目。

## 项目概述

本项目实现了一个完整的电商后台管理系统，包含商品管理、分类管理、订单管理、用户管理四大模块。后端采用 Spring Boot 3 + MyBatis Plus，前端采用 Vue 3 + Element Plus，通过 JWT 实现身份认证，并使用 Redis 缓存热点数据提升查询性能。

---

## 技术栈

### 后端
| 技术 | 说明 |
|------|------|
| Spring Boot 3.2.0 | 核心 Web 框架 |
| MyBatis Plus 3.5.5 | ORM 框架，简化 CRUD 与分页 |
| MySQL 8.0 | 关系型数据库 |
| Redis | 商品列表缓存（TTL 5min） |
| JWT (jjwt 0.12.3) | 用户身份认证与 Token 签发 |
| Spring Security + BCrypt | 密码加密存储 |

### 前端
| 技术 | 说明 |
|------|------|
| Vue 3 (Composition API) | 前端框架 |
| Vue Router | 路由管理与权限拦截 |
| Element Plus | UI 组件库 |
| Axios | HTTP 客户端，拦截器统一注入 Token |
| Pinia | 状态管理 |

---

## 项目结构

```
mall2/
├── backend/                          # Spring Boot 后端
│   └── src/main/java/com/mall/
│       ├── config/                   # 配置类
│       │   ├── SecurityConfig.java  # Spring Security 配置
│       │   └── RedisConfig.java     # Redis 序列化配置
│       ├── controller/               # 控制器层
│       │   ├── UserController.java  # 登录/注册/用户信息
│       │   ├── ProductController.java  # 商品 CRUD
│       │   ├── CategoryController.java # 分类 CRUD
│       │   └── OrderController.java    # 订单管理
│       ├── service/                 # 服务层
│       │   ├── impl/
│       │   └── ProductServiceImpl.java  # 动态条件分页查询
│       ├── mapper/                  # MyBatis Plus Mapper
│       ├── entity/                  # 实体类
│       ├── common/
│       │   └── Result.java          # 统一响应封装
│       └── utils/
│           └── JwtUtils.java        # Token 生成/解析/校验
│
├── frontend/                        # Vue 3 前端
│   └── src/
│       ├── views/                   # 页面组件
│       ├── router/index.js          # 路由 + 登录拦截
│       ├── utils/request.js         # Axios 实例（Token 注入 + 401 处理）
│       └── App.vue
│
└── docs/
    └── database.sql                 # 数据库建表脚本
```

---

## 快速启动

### 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis
- Node.js 18+

### 1. 初始化数据库

```bash
mysql -u root -p
source docs/database.sql
```

### 2. 启动后端

```bash
cd backend

# 修改 src/main/resources/application.yml 中的数据库密码
# spring.datasource.password: your_password

mvn clean package -DskipTests
java -jar target/mall-server-1.0.0.jar
```

后端地址：`http://localhost:8080`

### 3. 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端地址：`http://localhost:5173`

### 4. 测试账号

| 账号 | 密码 |
|------|------|
| admin | admin123 |

---

## API 接口

### 认证模块

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/user/register` | POST | 用户注册 |
| `/api/user/login` | POST | 登录，返回 JWT Token |
| `/api/user/info` | GET | 获取当前用户信息（需 Token） |
| `/api/user/update` | PUT | 更新用户信息（需 Token） |

### 商品模块

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/product/list` | GET | 分页查询（支持关键词搜索、分类筛选） |
| `/api/product/create` | POST | 新增商品 |
| `/api/product/update` | PUT | 修改商品 |
| `/api/product/delete/{id}` | DELETE | 删除商品 |
| `/api/product/detail/{id}` | GET | 商品详情 |

### 分类模块

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/category/list` | GET | 全量分类列表 |
| `/api/category/create` | POST | 新增分类 |
| `/api/category/update` | PUT | 修改分类 |
| `/api/category/delete/{id}` | DELETE | 删除分类 |

### 订单模块

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/order/list` | GET | 分页查询订单（支持订单号/收货人搜索） |
| `/api/order/status` | PUT | 更新订单状态（发货） |
| `/api/order/detail/{id}` | GET | 订单详情 |

---

## 核心设计亮点

### 1. JWT 身份认证
- 使用 `jjwt` 库签发 Token，携带 `userId` + `username`
- Token 有效期 24h，过期自动失效
- 前端 Axios 拦截器自动在请求头注入 `Authorization: Bearer xxx`
- 后端 Token 解析后校验签名与过期时间

### 2. Redis 缓存策略
- 商品列表页查询结果写入 Redis，Key 包含分页参数+搜索条件
- 缓存 TTL = 5 分钟，热点数据有效期内直接命中
- 写操作（新增/修改/删除）触发缓存清除，避免脏读

### 3. 统一响应封装
- 所有接口返回 `Result<T>` 结构：`{ code, message, data }`
- 前端 axios 拦截器统一处理，根据 code 判断成功/失败

### 4. MyBatis Plus 分页
- 使用 `LambdaQueryWrapper` 实现动态条件拼接
- 支持多字段模糊搜索（name / subtitle）
- 支持分类精准筛选与状态过滤
- 排序：按创建时间倒序

### 5. 密码安全
- 使用 Spring Security 的 `BCryptPasswordEncoder`
- 每次注册/登录时自动加盐哈希，相同密码每次哈希值不同
- 用户信息返回前主动置空 password 字段

---

## 数据库表结构

| 表名 | 说明 |
|------|------|
| `mall_user` | 用户表 |
| `mall_product` | 商品表 |
| `mall_category` | 分类表 |
| `mall_order` | 订单表 |
| `mall_order_item` | 订单明细表 |
