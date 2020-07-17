package com.bookkeeping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookkeeping.entity.PermissionModel;
import com.bookkeeping.entity.RoleModel;
import com.bookkeeping.entity.UserModel;
import com.bookkeeping.mapper.PermissionMapper;
import com.bookkeeping.mapper.RoleMapper;
import com.bookkeeping.service.PermissionService;
import com.bookkeeping.util.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionModel> implements PermissionService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Map<String, Object> getUserPerms(UserModel user) {
        Map<String, Object> data = new ConcurrentHashMap<>();
        String roleId = user.getRoleId();
        RoleModel role = roleMapper.selectById(roleId);
        if (null != role) {
            String permissions = role.getPermissions();
            String[] ids = permissions.split(",");
            List<PermissionModel> permissionList = new ArrayList<>();
            for (String id : ids) {
                // 角色对应的权限数据
                PermissionModel permissionModel = permissionMapper.selectById(id);
                if (null != permissionModel) {
                    //获取子权限
                    QueryWrapper<PermissionModel> wrapper = new QueryWrapper<PermissionModel>();
                    wrapper.lambda().eq(PermissionModel::getIsDeleted, 0).eq(PermissionModel::getPid, permissionModel.getId());
                    List<PermissionModel> children = permissionMapper.selectList(wrapper);
                    permissionModel.setChildrens(children);
                    permissionList.add(permissionModel);
                }
            }
            data.put("perm", permissionList);
        }
        return data;
    }

    @Override
    public Page<PermissionModel> findPage(PageModel<PermissionModel> pageModel) {
        QueryWrapper<PermissionModel> wrapper = commonWrapper(pageModel);
        return (Page<PermissionModel>) super.page(pageModel, wrapper);
    }


    private QueryWrapper<PermissionModel> commonWrapper(PageModel<PermissionModel> pageModel) {
        Map paramMap = pageModel.getCondition();
        QueryWrapper<PermissionModel> wrapper = new QueryWrapper<PermissionModel>();
        wrapper.eq("is_deleted", 0);
        String name = "name";
        paramMap.forEach((k, v) -> {
            if (!StringUtils.isEmpty(v)) {
                if (name.equals(k)) {
                    wrapper.like("name", v);
                }
            }
        });
        return wrapper;
    }
}
