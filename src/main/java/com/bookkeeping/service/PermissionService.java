package com.bookkeeping.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookkeeping.entity.PermissionModel;
import com.bookkeeping.entity.UserModel;
import com.bookkeeping.util.PageModel;

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

    Page<PermissionModel> findPage(PageModel<PermissionModel> pageModel);

}
