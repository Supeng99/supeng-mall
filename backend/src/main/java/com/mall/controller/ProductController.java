package com.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.Result;
import com.mall.entity.Product;
import com.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductController {

    private static final String PRODUCT_PAGE_KEY = "mall:product:page:";

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/list")
    public Result<Page<Product>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId) {

        String cacheKey = PRODUCT_PAGE_KEY + pageNum + ":" + pageSize + ":" + (keyword == null ? "" : keyword) + ":" + (categoryId == null ? "" : categoryId);

        // 尝试从 Redis 缓存读取
        Page<Product> cached = (Page<Product>) redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return Result.success(cached);
        }

        // 缓存未命中，查询数据库
        Page<Product> page = productService.getProductPage(pageNum, pageSize, keyword, categoryId);

        // 写入 Redis，缓存 5 分钟
        redisTemplate.opsForValue().set(cacheKey, page, 5, TimeUnit.MINUTES);

        return Result.success(page);
    }

    @PostMapping("/create")
    public Result<Product> create(
            @RequestParam String name,
            @RequestParam(required = false) String subtitle,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Integer stock) {
        Product product = productService.createProduct(name, subtitle, categoryId, price, stock);
        clearProductCache();
        return Result.success(product);
    }

    @PutMapping("/update")
    public Result<Product> update(
            @RequestParam Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String subtitle,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Integer stock) {
        Product product = productService.updateProduct(id, name, subtitle, categoryId, price, stock);
        clearProductCache();
        return Result.success(product);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        productService.removeById(id);
        clearProductCache();
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result<Product> detail(@PathVariable Long id) {
        return Result.success(productService.getById(id));
    }

    /**
     * 写操作后清空商品列表缓存（简化处理：清除所有分页缓存）
     */
    private void clearProductCache() {
        try {
            var keys = redisTemplate.keys(PRODUCT_PAGE_KEY + "*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
        } catch (Exception ignored) {
        }
    }
}
