package com.fnmain.service.impl;


import com.fnmain.mapper.CategoryMapper;
import com.fnmain.pojo.Category;
import com.fnmain.service.CategoryService;
import com.fnmain.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public void add(Category category) {

        //补充属性值
        TimeUtils.setTime(category);

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);

        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {

        Map<String, Object> m = ThreadLocalUtil.get();
        Integer id = (Integer) m.get("id");

        //当前用户Id
        return categoryMapper.list(id);
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }
}
