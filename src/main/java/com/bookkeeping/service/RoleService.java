package com.bookkeeping.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookkeeping.entity.RoleModel;
import com.bookkeeping.util.PageModel;

/**
 * <p>
 * 系统用户角色表 服务类
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */
public interface RoleService extends IService<RoleModel> {
    Page<RoleModel> findPage(PageModel<RoleModel> pageModel);

}
