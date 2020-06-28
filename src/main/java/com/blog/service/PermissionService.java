package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.PermissionModel;
import com.blog.entity.UserModel;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */
public interface PermissionService extends IService<PermissionModel> {

    Map<String, Object> getUserPerms(UserModel user);

}
