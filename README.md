# Mall 电商系统 - 面试项目

## 项目简介

这是一个完整的前后端分离电商系统，适合作为实习/校招面试项目展示。

## 技术栈

### 后端
- **Spring Boot 3.2.0** - Java Web 框架
- **MyBatis Plus 3.5.5** - ORM 框架
- **MySQL 8.0** - 关系型数据库
- **Redis** - 缓存
- **JWT** - 用户认证

### 前端
- **Vue 3** - 前端框架
- **Vue Router** - 路由管理
- **Element Plus** - UI 组件库
- **Axios** - HTTP 客户端
- **Pinia** - 状态管理

## 项目结构

```
mall2/
├── backend/                    # 后端 Spring Boot 项目
│   ├── src/main/java/com/mall/
│   │   ├── config/            # 配置类
│   │   ├── controller/        # 控制器层
│   │   ├── entity/           # 实体类
│   │   ├── mapper/           # 数据访问层
│   │   ├── service/          # 服务层
│   │   ├── service/impl/     # 服务实现
│   │   ├── common/           # 通用类
│   │   └── utils/            # 工具类
│   └── src/main/resources/
│       └── application.yml   # 配置文件
├── frontend/                   # 前端 Vue3 项目
│   └── src/
│       ├── views/            # 页面组件
│       ├── router/           # 路由配置
│       └── App.vue           # 根组件
└── docs/                      # 文档
    └── database.sql          # 数据库脚本
```

## 快速启动

### 1. 环境要求
- JDK 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库配置

```bash
# 登录 MySQL
mysql -u root -p

# 执行数据库脚本
source docs/database.sql
```

### 3. 启动后端

```bash
cd backend

# 修改 application.yml 中的数据库配置
# spring.datasource.username=root
# spring.datasource.password=your_password

# 打包并启动
mvn clean package -DskipTests
java -jar target/mall-server-1.0.0.jar
```

后端启动地址: http://localhost:8080

### 4. 启动前端

```bash
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端启动地址: http://localhost:5173

### 5. 登录信息
- 用户名: admin
- 密码: admin123

## 功能模块

### 管理端功能
1. **仪表盘** - 数据统计展示
2. **商品管理** - 商品的增删改查
3. **分类管理** - 商品分类管理
4. **订单管理** - 订单查看与发货
5. **用户管理** - 用户信息查看

### 核心接口

| 接口路径 | 方法 | 说明 |
|---------|------|------|
| /api/user/login | POST | 用户登录 |
| /api/user/register | POST | 用户注册 |
| /api/product/list | GET | 商品列表 |
| /api/order/list | GET | 订单列表 |

