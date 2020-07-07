package com.blog.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.entity.UserModel;
import com.blog.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: MyRealm
 * @Description:
 * @author: dongwn
 * @version: 1.0
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;


    /**
     * 功能描述: 授权
     *
     * @param:
     * @return:
     * @auther: dongwn
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        // 添加权限 和 角色信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }


    /**
     * 功能描述: 认证
     *
     * @param:
     * @return:
     * @auther: dongwn
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken用于存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        // 调用数据层
        QueryWrapper<UserModel> wrapper = new QueryWrapper<UserModel>();
        wrapper.lambda().eq(UserModel::getName, username);
        UserModel sysUser = userService.getOne(wrapper);
        if (sysUser == null) {
            // 用户不存在
            return null;
        }
        // 返回密码
        return new SimpleAuthenticationInfo(sysUser, sysUser.getPwd(), ByteSource.Util.bytes(username), getName());

    }

    /**
     * 设置认证加密方式
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        MyCredentialsMatcher customCredentialsMatcher = new MyCredentialsMatcher();
        super.setCredentialsMatcher(customCredentialsMatcher);
    }


}
