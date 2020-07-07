package com.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.entity.PermissionModel;
import com.blog.entity.UserModel;
import com.blog.service.PermissionService;
import com.blog.util.PageModel;
import com.blog.util.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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


    @GetMapping("/permissionList")
    public String roleList() {
        return "/permission/permissionList";

    }

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


    @PostMapping("/permissionList")
    @ResponseBody
    public Map<String, Object> list(@RequestBody PageModel<PermissionModel> pageModel) {
        pageModel = (PageModel<PermissionModel>) permissionService.findPage(pageModel);
        return Result.ok(pageModel);
    }


    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> saveOrUpdate(@RequestBody PermissionModel permissionModel) {
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(permissionModel.getId())) {
            permissionModel.setCreateTime(new Date());
            permissionModel.setCreateBy(user.getId());
            permissionService.save(permissionModel);
        } else {
            permissionModel.setUpdateTime(new Date());
            permissionModel.setUpdateBy(user.getId());
            permissionService.updateById(permissionModel);
        }
        return Result.ok("操作成功!");
    }


    @DeleteMapping("/delBatchByIds")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody String[] ids) {
        List<String> idList = Arrays.asList(ids);
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        List<PermissionModel> roleModelList = new ArrayList<>();
        for (String id : idList) {
            PermissionModel permissionModel = new PermissionModel();
            permissionModel.setId(id);
            permissionModel.setIsDeleted(1);
            permissionModel.setUpdateBy(user.getId());
            permissionModel.setUpdateTime(new Date());
            roleModelList.add(permissionModel);
        }
        permissionService.updateBatchById(roleModelList);
        return Result.ok("操作成功");
    }


}
