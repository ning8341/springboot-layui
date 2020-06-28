package com.blog.controller;


import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/articleManage")
    public String toBlogPage() {
        return "/article/articleManage";
    }





}
