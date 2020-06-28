package com.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.UserModel;
import com.blog.util.PageModel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统管理员帐号 服务类
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */
public interface UserService extends IService<UserModel> {

    Page<UserModel> findPage(PageModel<UserModel> pageModel, Map paramMap);

    List<UserModel> findList(Map paramMap);

}
