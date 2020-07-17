package com.bookkeeping.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookkeeping.entity.RoleModel;
import com.bookkeeping.entity.UserModel;
import com.bookkeeping.service.RoleService;
import com.bookkeeping.util.PageModel;
import com.bookkeeping.util.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 系统用户角色表前端控制器
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/roleList")
    public String roleList() {
        return "/role/roleList";

    }

    @GetMapping("/getRoles")
    @ResponseBody
    public List<RoleModel> getRoles() {
        QueryWrapper<RoleModel> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RoleModel::getIsDeleted, 0);
        return roleService.list(wrapper);
    }


    @PostMapping("/getRoleList")
    @ResponseBody
    public Map<String, Object> list(@RequestBody PageModel<RoleModel> pageModel) {
        pageModel = (PageModel<RoleModel>) roleService.findPage(pageModel);
        return Result.ok(pageModel);
    }

    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> saveOrUpdate(@RequestBody RoleModel roleModel) {
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(roleModel.getId())) {
            roleModel.setCreateTime(new Date());
            roleModel.setCreateBy(user.getId());
            roleService.save(roleModel);
        } else {
            roleModel.setUpdateTime(new Date());
            roleModel.setUpdateBy(user.getId());
            roleService.updateById(roleModel);
        }
        return Result.ok("操作成功!");
    }


    @DeleteMapping("/delBatchByIds")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody String[] ids) {
        List<String> idList = Arrays.asList(ids);
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        List<RoleModel> roleModelList = new ArrayList<>();
        for (String id : idList) {
            RoleModel roleModel = new RoleModel();
            roleModel.setId(id);
            roleModel.setIsDeleted(1);
            roleModel.setUpdateBy(user.getId());
            roleModel.setUpdateTime(new Date());
            roleModelList.add(roleModel);
        }
        roleService.updateBatchById(roleModelList);
        return Result.ok("操作成功");
    }

    @PostMapping("/updateStatus")
    @ResponseBody
    public Map<String, Object> updateUserStatus(@RequestParam("id") String id, @RequestParam("status") Integer status) {
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        RoleModel roleModel = new RoleModel();
        roleModel.setId(id);
        roleModel.setRoleStatus(status);
        roleModel.setUpdateTime(new Date());
        roleModel.setUpdateBy(user.getId());
        roleService.updateById(roleModel);
        return Result.ok("操作成功");
    }


}

