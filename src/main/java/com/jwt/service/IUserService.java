package com.jwt.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jwt.entity.UserModel;
import com.jwt.util.Result;

import java.util.Map;
import java.util.Set;

public interface IUserService extends IService<UserModel> {


    /**
     * 通过用户名获取用户角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    Set<String> getUserRolesSet(String username);

    /**
     * 通过用户名获取用户权限集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    Set<String> getUserPermissionsSet(String username);

    /**
     * 校验用户是否有效
     *
     * @param sysUser
     * @return
     */
    Map<String, Object> checkUserIsEffective(UserModel sysUser);

}
