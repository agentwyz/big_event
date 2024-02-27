package com.fnmain.service;

import com.fnmain.pojo.Category;

import java.util.List;

public interface CategoryService {
    //新增分类
    void add(Category category);

    //列表查询
    List<Category> list();

    Category findById(Integer id);

    void update(Category category);
}
