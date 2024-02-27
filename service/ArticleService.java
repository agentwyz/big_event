package com.fnmain.service;

import com.fnmain.pojo.Article;
import com.fnmain.pojo.PageBean;


public interface ArticleService {

    void add(Article article);

    void update(Article article);

    Article findById(Integer id);

    void delete(Integer id);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
