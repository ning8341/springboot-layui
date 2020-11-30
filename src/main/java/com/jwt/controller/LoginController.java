package com.jwt.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwt.constant.Constant;
import com.jwt.entity.UserModel;
import com.jwt.service.IUserService;
import com.jwt.util.JwtUtil;
import com.jwt.util.PasswordUtil;
import com.jwt.util.RedisUtil;
import com.jwt.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/sys")
@Slf4j
public class LoginController {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserModel loginUser) throws Exception {
        Map<String, Object> resMap = new ConcurrentHashMap<>();
        String username = loginUser.getUserName();
        String password = loginUser.getPassWord();
        UserModel sysUser = getUserModel(username);
        resMap = userService.checkUserIsEffective(sysUser);
        if (resMap.get("status").equals("error")) {
            return resMap;
        }
        String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
        String syspassword = sysUser.getPassWord();
        if (!syspassword.equals(userpassword)) {
            return Result.error("用户名或密码错误");
        }
        assembInfo(sysUser, resMap);
        return resMap;
    }

    private Map<String, Object> assembInfo(UserModel sysUser, Map<String, Object> resMap) {
        String syspassword = sysUser.getPassWord();
        String username = sysUser.getUserName();
        String token = JwtUtil.sign(username, syspassword);
        redisUtil.set(Constant.PREFIX_USER_TOKEN + token, token, JwtUtil.EXPIRE_TIME / 1000);
        resMap.put("token", token);
        resMap.put("userInfo", sysUser);
        return resMap;
    }

    private UserModel getUserModel(String username) {
        QueryWrapper<UserModel> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserModel::getUserName, username).eq(UserModel::getIsDeleted, 0);
        return userService.getOne(wrapper);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        //用户退出逻辑
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return Result.error("退出登录失败！");
        }
        String username = JwtUtil.getUsername(token);
        UserModel sysUser = getUserModel(username);
        if (sysUser != null) {
            Object value = redisUtil.get(Constant.PREFIX_USER_TOKEN + token);
            if (value != null) {
                if (value.toString().equals(token)) {
                    //清空用户Token缓存
                    redisUtil.del(Constant.PREFIX_USER_TOKEN + token);
                    //清空用户权限缓存：权限Perms和角色集合
                    redisUtil.del(Constant.LOGIN_USER_CACHERULES_ROLE + username);
                    redisUtil.del(Constant.LOGIN_USER_CACHERULES_PERMISSION + username);
                    return Result.ok("退出登录成功！");
                } else {
                    return Result.error("token不匹配");
                }
            } else {
                return Result.error("token过期");
            }
        } else {
            return Result.error("无效的token");
        }
    }


}
