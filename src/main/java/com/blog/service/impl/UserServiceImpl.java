package com.blog.service.impl;


import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.mapper.UserMapper;
import com.blog.entity.UserModel;
import com.blog.service.UserService;
import com.blog.util.PageModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统管理员帐号 服务实现类
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements UserService {

    @Override
    public Page<UserModel> findPage(PageModel<UserModel> pageModel, Map paramMap) {
        QueryWrapper<UserModel> wrapper = commonWrapper(paramMap);
        return (Page<UserModel>) super.page(pageModel, wrapper);
    }

    @Override
    public List<UserModel> findList(Map paramMap) {
        QueryWrapper<UserModel> wrapper = commonWrapper(paramMap);
        return super.list(wrapper);
    }

    private QueryWrapper<UserModel> commonWrapper(Map paramMap) {
        QueryWrapper<UserModel> wrapper = new QueryWrapper<UserModel>();
        wrapper.eq("is_deleted", 0);
        String name = Convert.toStr(paramMap.get("name"));
        String phone = Convert.toStr(paramMap.get("phone"));
        paramMap.forEach((k, v) -> {
            if (name.equals(k)) {
                wrapper.like("name", name);
            }
            if (phone.equals(k)) {
                wrapper.like("phone", phone);
            }
        });
        return wrapper;
    }
}
