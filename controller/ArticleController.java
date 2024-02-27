package com.fnmain.controller;


import com.fnmain.pojo.Article;
import com.fnmain.pojo.PageBean;
import com.fnmain.pojo.Result;
import com.fnmain.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    //使用@Validated才能生效
    @PostMapping
    public Result add(@RequestBody @Validated Article article) { //validated是为了可以生效
        articleService.add(article);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Article article) {
        articleService.update(article);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result detail(Integer id) {
        Article article = articleService.findById(id);
        return Result.success(article);
    }

    @DeleteMapping
    public Result delete(Integer id) {
       articleService.delete(id);
       return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
        PageBean<Article> pageBean =  articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pageBean);
    }

}
