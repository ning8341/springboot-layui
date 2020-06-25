package com.blog.manager.controller;


import com.blog.manager.response.PageDataResult;
import com.blog.manager.service.ArticleService;
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

    @GetMapping("/getArticleList")
    @ResponseBody
    public PageDataResult getRoleList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize) {
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            // 获取角色列表
            pdr = articleService.getArticleList(pageNum ,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdr;
    }



}
