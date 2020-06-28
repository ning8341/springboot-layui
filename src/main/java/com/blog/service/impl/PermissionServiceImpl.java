package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.RoleModel;
import com.blog.entity.UserModel;
import com.blog.mapper.PermissionMapper;
import com.blog.entity.PermissionModel;
import com.blog.mapper.RoleMapper;
import com.blog.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  服务实现类
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
        if (null != role ) {
            String permissions = role.getPermissions();
            String[] ids = permissions.split(",");
            List<PermissionModel> permissionList = new ArrayList<>();
            for (String id : ids) {
                // 角色对应的权限数据
                PermissionModel permissionModel = permissionMapper.selectById(id);
                if (null != permissionModel ) {
                    //获取子权限
                    QueryWrapper<PermissionModel> wrapper = new QueryWrapper<PermissionModel>();
                    wrapper.lambda().eq(PermissionModel::getIsDeleted,0).eq(PermissionModel::getPid,permissionModel.getId());
                    List<PermissionModel> children = permissionMapper.selectList(wrapper);
                    permissionModel.setChildrens(children);
                    permissionList.add(permissionModel);
                }
            }
            data.put("perm",permissionList);
        }
        return data;
    }
}
