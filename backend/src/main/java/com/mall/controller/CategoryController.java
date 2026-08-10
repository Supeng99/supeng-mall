package com.mall.controller;

import com.mall.common.Result;
import com.mall.entity.Category;
import com.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<Category>> list() {
        return Result.success(categoryService.getAllCategories());
    }

    @PostMapping("/create")
    public Result<Category> create(@RequestParam String name,
                                   @RequestParam(required = false) String icon) {
        return Result.success(categoryService.createCategory(name, icon));
    }

    @PutMapping("/update")
    public Result<Category> update(@RequestParam Long id,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String icon) {
        return Result.success(categoryService.updateCategory(id, name, icon));
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
