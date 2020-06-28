package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.mapper.RoleMapper;
import com.blog.entity.RoleModel;
import com.blog.service.RoleService;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 系统用户角色表 服务实现类
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleModel> implements RoleService {

}
