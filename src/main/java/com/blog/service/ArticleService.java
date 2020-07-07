package com.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.ArticleModel;
import com.blog.util.PageModel;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */
public interface ArticleService extends IService<ArticleModel> {
    Page<ArticleModel> findPage(PageModel<ArticleModel> pageModel);

}
