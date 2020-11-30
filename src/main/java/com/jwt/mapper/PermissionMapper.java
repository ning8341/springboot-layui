package com.jwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwt.entity.PermissionModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表 Mapper 接口
 */
public interface PermissionMapper extends BaseMapper<PermissionModel> {

    /**
     * 根据用户查询用户权限
     */
    public List<PermissionModel> queryByUser(@Param("username") String username);

}
