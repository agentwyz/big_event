package com.fnmain.controller;


import com.fnmain.pojo.Category;
import com.fnmain.pojo.Result;
import com.fnmain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);

        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list() {
        List<Category> categoryList = categoryService.list();
        return Result.success(categoryList);
    }


    @GetMapping("/detail")
    public Result detail(Integer id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return Result.error("id不存在");
        }

        return Result.success(category);
    }
}
