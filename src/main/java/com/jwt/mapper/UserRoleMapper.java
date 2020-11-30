package com.jwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwt.entity.UserRoleModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色表 Mapper 接口
 */
public interface UserRoleMapper extends BaseMapper<UserRoleModel> {

    @Select("select id from t_role where id in (select role_id from t_user_role where user_id = (select id from t_user where user_name=#{username}))")
    List<String> getRoleByUserName(@Param("username") String username);
}
