package com.blog.controller;


import com.blog.entity.ArticleModel;
import com.blog.service.ArticleService;
import com.blog.util.PageModel;
import com.blog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 跳转博客相关操作页面
     *
     * @return
     */
    @GetMapping("/articleList")
    public String toBlogPage() {
        return "/article/articleList";
    }


    @PostMapping("/articleList")
    @ResponseBody
    public Map<String, Object> list(@RequestBody PageModel<ArticleModel> pageModel) {
        pageModel = (PageModel<ArticleModel>) articleService.findPage(pageModel);
        return Result.ok(pageModel);
    }





}
