package com.jwt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwt.entity.UserModel;
import com.jwt.service.IUserService;
import com.jwt.util.PageModel;
import com.jwt.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequiresPermissions("user:list")
    @RequestMapping("/list")
    public Map<String, Object> userList(HttpServletRequest request) {
        PageModel<UserModel> pageModel = new PageModel<UserModel>(request);
        QueryWrapper<UserModel> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserModel::getIsDeleted, 0);
        pageModel = (PageModel<UserModel>) userService.page(pageModel, wrapper);
        return Result.ok(pageModel);
    }

    @RequiresPermissions("user:add")
    @RequestMapping("/add")
    public Map<String, Object> userAdd() {
        return Result.ok("新增用户");
    }

    @RequiresPermissions("user:delete")
    @RequestMapping("/delete")
    public Map<String, Object> userDelete() {
        return Result.ok("删除用户");
    }

    @RequiresPermissions("user:update")
    @RequestMapping("/update")
    public Map<String, Object> userUpdate() {
        return Result.ok("修改用户");
    }

    @RequestMapping("/test")
    public Map<String, Object> test() {
        return Result.ok("不用登陆直接访问的接口");
    }
}
