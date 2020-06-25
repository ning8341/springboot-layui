package com.blog.manager.dao;

import com.blog.manager.pojo.BaseArticle;
import org.springframework.stereotype.Repository;
import tk.mapper.MyMapper;

import java.util.List;

@Repository
public interface ArticleMapper extends MyMapper<BaseArticle> {

    List<BaseArticle> selectArticleList();
}
