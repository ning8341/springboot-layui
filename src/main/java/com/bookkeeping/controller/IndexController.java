package com.bookkeeping.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookkeeping.entity.UserModel;
import com.bookkeeping.service.UserService;
import com.bookkeeping.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class IndexController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String tologin() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        // shiro底层删除session的会话信息
        subject.logout();
        return "redirect:login";
    }


    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(String name, String pwd, HttpServletRequest request, HttpSession session) {
        // 使用 shiro 进行登录
        Subject subject = SecurityUtils.getSubject();
        String host = request.getRemoteAddr();
        QueryWrapper<UserModel> wrapper = new QueryWrapper<UserModel>();
        wrapper.lambda().eq(UserModel::getIsDeleted, 0).eq(UserModel::getStatus, 1).eq(UserModel::getName, name);
        UserModel dbUser = userService.getOne(wrapper);
        if (null == dbUser) {
           return Result.error("500", "您无权限登录,请联系管理员!");
        }
        //获取token
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd, host);
        try {
            subject.login(token);
            // 登录成功
            UserModel user = (UserModel) subject.getPrincipal();
            session.setAttribute("user", user.getName());
            return Result.ok("0", "/home");
        } catch (UnknownAccountException e) {
            return Result.error("-1", "账号不存在");
        } catch (DisabledAccountException e) {
            return Result.error("-1", "账号异常");
        } catch (AuthenticationException e) {
            return Result.error("-1", "密码错误");
        }
    }

}
