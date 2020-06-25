package com.blog.manager.service;

import com.blog.manager.response.PageDataResult;
import com.blog.manager.pojo.BaseAdminUser;
import com.blog.manager.dto.UserSearchDTO;

import java.util.Map;


/**
 * @Title: AdminUserService
 * @Description:
 * @author: dongwn
 * @version: 1.0
 */
public interface AdminUserService {

    PageDataResult getUserList(UserSearchDTO userSearch, Integer pageNum, Integer pageSize);

    Map<String,Object> addUser(BaseAdminUser user);

    Map<String,Object> updateUser(BaseAdminUser user);

    BaseAdminUser getUserById(Integer id);

    BaseAdminUser findByUserName(String userName);

    int updatePwd(String userName,String password);

    Map<String, Object> delUser(Integer id,Integer status);

    Map<String, Object> recoverUser(Integer id,Integer status);
}
