package com.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.entity.PermissionModel;
import com.blog.entity.UserModel;
import com.blog.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 获取登录用户的权限
     *
     * @return
     */
    @GetMapping("/getUserPerms")
    @ResponseBody
    public Map<String, Object> getUserPerms() {
        Map<String, Object> data = new ConcurrentHashMap<>();
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        data = permissionService.getUserPerms(user);
        return data;
    }


    @GetMapping("/parentPermissionList")
    @ResponseBody
    public List<PermissionModel> parentPermissionList() {
        QueryWrapper<PermissionModel> wrapper = new QueryWrapper<PermissionModel>();
        wrapper.lambda().eq(PermissionModel::getPid, 0).eq(PermissionModel::getIsDeleted, 0);
        return permissionService.list(wrapper);
    }
}
