package com.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.mapper.ArticleMapper;
import com.blog.entity.ArticleModel;
import com.blog.service.ArticleService;
import com.blog.util.PageModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleModel> implements ArticleService {

    @Override
    public Page<ArticleModel> findPage(PageModel<ArticleModel> pageModel) {
        QueryWrapper<ArticleModel> wrapper = commonWrapper(pageModel);
        return (Page<ArticleModel>) super.page(pageModel, wrapper);
    }




    private QueryWrapper<ArticleModel> commonWrapper(PageModel<ArticleModel> pageModel) {
        Map paramMap = pageModel.getCondition();
        QueryWrapper<ArticleModel> wrapper = new QueryWrapper<ArticleModel>();
        wrapper.eq("is_deleted", 0);
        String name = "title";
        paramMap.forEach((k, v) -> {
            if (!StringUtils.isEmpty(v)) {
                if (name.equals(k)) {
                    wrapper.like("title", v);
                }
            }
        });
        return wrapper;
    }
}
