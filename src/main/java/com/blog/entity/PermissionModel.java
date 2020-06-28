package com.blog.entity;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * @author dongwn
 * @since 2020-06-28
 */
@Data
@TableName("t_permission")
public class PermissionModel {


    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "pid")
    private String pid;

    @TableField(value = "descpt")
    private String descpt;

    @TableField(value = "url")
    private String url;

    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "create_by")
    private String createBy;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "update_by")
    private String updateBy;

    @TableField(exist = false)
    private List<PermissionModel> childrens;



}


