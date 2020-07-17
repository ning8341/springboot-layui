package com.bookkeeping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookkeeping.mapper.RoleMapper;
import com.bookkeeping.entity.RoleModel;
import com.bookkeeping.service.RoleService;
import com.bookkeeping.util.PageModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * <p>
 * 系统用户角色表 服务实现类
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleModel> implements RoleService {

    @Override
    public Page<RoleModel> findPage(PageModel<RoleModel> pageModel) {
        QueryWrapper<RoleModel> wrapper = commonWrapper(pageModel);
        return (Page<RoleModel>) super.page(pageModel, wrapper);
    }

    private QueryWrapper<RoleModel> commonWrapper(PageModel<RoleModel> pageModel) {
        Map paramMap = pageModel.getCondition();
        QueryWrapper<RoleModel> wrapper = new QueryWrapper<RoleModel>();
        wrapper.eq("is_deleted", 0);
        String name = "role_name";
        paramMap.forEach((k, v) -> {
            if(!StringUtils.isEmpty(v)){
                if (name.equals(k)) {
                    wrapper.like("role_name", v);
                }
            }
        });
        return wrapper;
    }
}
