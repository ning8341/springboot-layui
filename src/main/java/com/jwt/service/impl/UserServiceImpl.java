package com.jwt.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwt.constant.Constant;
import com.jwt.entity.PermissionModel;
import com.jwt.entity.UserModel;
import com.jwt.mapper.PermissionMapper;
import com.jwt.mapper.UserMapper;
import com.jwt.mapper.UserRoleMapper;
import com.jwt.service.IUserService;
import com.jwt.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户表 服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper sysPermissionMapper;
    @Autowired
    private UserRoleMapper sysUserRoleMapper;

    /**
     * 通过用户名获取用户角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    @Override
    @Cacheable(value = Constant.LOGIN_USER_RULES_CACHE, key = "'Roles_'+#username")
    public Set<String> getUserRolesSet(String username) {
        // 查询用户拥有的角色集合
        List<String> roles = sysUserRoleMapper.getRoleByUserName(username);
        log.info("-------通过数据库读取用户拥有的角色Rules------username： " + username + ",Roles size: " + (roles == null ? 0 : roles.size()));
        return new HashSet<>(roles);
    }

    /**
     * 通过用户名获取用户权限集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    @Override
    @Cacheable(value = Constant.LOGIN_USER_RULES_CACHE, key = "'Permissions_'+#username")
    public Set<String> getUserPermissionsSet(String username) {
        Set<String> permissionSet = new HashSet<>();
        List<PermissionModel> permissionList = sysPermissionMapper.queryByUser(username);
        for (PermissionModel po : permissionList) {
//			if (oConvertUtils.isNotEmpty(po.getUrl())) {
//				permissionSet.add(po.getUrl());
//			}
            if (!StringUtils.isEmpty(po.getPerms())) {
                permissionSet.add(po.getPerms());
            }
        }
        log.info("-------通过数据库读取用户拥有的权限Perms------username： " + username + ",Perms size: " + (permissionSet == null ? 0 : permissionSet.size()));
        return permissionSet;
    }

    /**
     * 校验用户是否有效
     *
     * @param sysUser
     * @return
     */
    @Override
    public Map<String, Object> checkUserIsEffective(UserModel sysUser) {
        if (sysUser == null) {
            return Result.error("该用户不存在，请注册");
        }

        if (sysUser.getIsDeleted()==1) {
            return Result.error("该用户已被删除");
        }
        return Result.ok();
    }
}
