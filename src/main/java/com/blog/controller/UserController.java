package com.blog.controller;


import cn.hutool.crypto.SecureUtil;
import com.blog.entity.UserModel;
import com.blog.service.UserService;
import com.blog.util.PageModel;
import com.blog.util.Result;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * <p>
 * 系统管理员帐号前端控制器
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userList")
    public String userManage() {
        return "/user/userList";
    }

    @PostMapping("/list")
    @ResponseBody
    public Map<String, Object> list(@RequestBody PageModel<UserModel> pageModel) {
        pageModel = (PageModel<UserModel>) userService.findPage(pageModel);
        return Result.ok(pageModel);

    }

    /**
     * @param userModel 去掉校验数据逻辑
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> saveOrUpdate(@RequestBody UserModel userModel) {
        String pwd = userModel.getPwd();
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(userModel.getId())) {
            userModel.setCreateTime(new Date());
            userModel.setCreateBy(user.getId());
            userModel.setPwd(SecureUtil.md5(pwd));
            userService.save(userModel);
        } else {
            if (!StringUtils.isEmpty(pwd)) {
                userModel.setPwd(SecureUtil.md5(pwd));
            }
            userModel.setUpdateTime(new Date());
            userModel.setUpdateBy(user.getId());
            userService.updateById(userModel);
        }
        return Result.ok("操作成功!");
    }


    @PostMapping("/updateUserStatus")
    @ResponseBody
    public Map<String, Object> updateUserStatus(@RequestParam("id") String id, @RequestParam("status") Integer status) {
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        UserModel userModel = new UserModel();
        userModel.setId(id);
        userModel.setStatus(status);
        userModel.setUpdateTime(new Date());
        userModel.setUpdateBy(user.getId());
        userService.updateById(userModel);
        return Result.ok("操作成功");
    }

    @DeleteMapping("/delBatchByIds")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody String[] ids) {
        List<String> idList = Arrays.asList(ids);
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        List<UserModel> userList = new ArrayList<>();
        for (String id : idList) {
            UserModel userModel = new UserModel();
            userModel.setId(id);
            userModel.setIsDeleted(1);
            userModel.setUpdateBy(user.getId());
            userModel.setUpdateTime(new Date());
            userList.add(userModel);
        }
        userService.updateBatchById(userList);
        return Result.ok("操作成功");
    }

    /**
     * 简单写，密码校验跳过
     *
     * @return
     */
    @PostMapping("/setPwd")
    @ResponseBody
    public Map<String, Object> updatePwd(@RequestBody UserModel userModel) {
        String pwd = userModel.getPwd();
        userModel.setPwd(SecureUtil.md5(pwd));
        userModel.setUpdateTime(new Date());
        userService.updateById(userModel);
        return Result.ok("操作成功");
    }


}
