package com.blog.manager.service;

import com.blog.manager.response.PageDataResult;
import com.blog.manager.dto.PermissionDTO;
import com.blog.manager.pojo.BaseAdminUser;
import com.blog.manager.pojo.BaseAdminPermission;

import java.util.List;
import java.util.Map;

/**
 * @Title: PermissionService
 * @Description:
 * @author: dongwn
 * @version: 1.0
 */
public interface AdminPermissionService {

    /**
     *
     * 功能描述: 添加权限
     *
     * @param:
     * @return:
     * @auther: dongwn
     */
    Map<String,Object> addPermission(BaseAdminPermission permission);

    /**
     *
     * 功能描述: 修改权限
     *
     * @param:
     * @return:
     * @auther: dongwn
     */
    Map<String,Object> updatePermission(BaseAdminPermission permission);

    /**
     *
     * 功能描述: 获取权限菜单列表
     *
     * @param:
     * @return:
     * @auther: dongwn
     */
    PageDataResult getPermissionList(Integer pageNum, Integer pageSize);

    /**
     *
     * 功能描述: 获取根权限菜单列表
     *
     * @param:
     * @return:
     * @auther: dongwn
     */
    List<PermissionDTO> parentPermissionList();

    /**
     *
     * 功能描述: 删除权限菜单
     *
     * @param:
     * @return:
     * @auther: dongwn
     */
    Map<String, Object> del(long id);

    /**
     *
     * 功能描述: 根据id获取权限
     *
     * @param:
     * @return:
     * @auther: dongwn
     */
    BaseAdminPermission getById(Object id);


    /**
     *
     * 功能描述: 获取当前登陆用户的权限
     *
     * @param:
     * @return:
     * @auther: dongwn
     */
    Map<String, Object> getUserPerms(BaseAdminUser user);

}
