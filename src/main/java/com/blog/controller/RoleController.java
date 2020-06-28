package com.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.entity.RoleModel;
import com.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @GetMapping("getRoles")
    @ResponseBody
    public List<RoleModel> getRoles() {
        QueryWrapper<RoleModel> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RoleModel::getIsDeleted, 0);
        return roleService.list(wrapper);
    }

}

