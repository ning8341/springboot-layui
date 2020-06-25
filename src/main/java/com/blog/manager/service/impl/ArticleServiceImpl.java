package com.blog.manager.service.impl;

import com.blog.manager.dao.ArticleMapper;
import com.blog.manager.pojo.BaseArticle;
import com.blog.manager.response.PageDataResult;
import com.blog.manager.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public PageDataResult getArticleList(Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        List<BaseArticle> articleList = articleMapper.selectArticleList();
        PageHelper.startPage(pageNum, pageSize);
        if(null!= articleList && articleList.size() != 0){
            PageInfo<BaseArticle> pageInfo = new PageInfo<>();
            pageDataResult.setList(articleList);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
