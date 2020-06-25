package com.blog.manager.service;

import com.blog.manager.response.PageDataResult;

public interface ArticleService {

    PageDataResult getArticleList(Integer pageNum, Integer pageSize);
}
