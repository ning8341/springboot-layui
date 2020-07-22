package com.bookkeeping.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


/**
* @author dongwn
* @since 2020-06-28
*/
@Data
@TableName("t_user")
public class UserModel {


    @TableId(value="id", type= IdType.UUID)
	private String id;
    
    @TableField(value = "name")
    private String name; 

    @JsonIgnore
    @TableField(value = "pwd")
    private String pwd; 
    
    @TableField(value = "role_id")
    private String roleId;
    
    @TableField(value = "phone")
    private String phone; 
    
    @TableField(value = "status")
    private Integer status; 
    
    @TableField(value = "remark")
    private String remark; 
    
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "create_time")
    private Date createTime; 
    
    @TableField(value = "create_by")
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "update_time")
    private Date updateTime; 
    
    @TableField(value = "update_by")
    private String updateBy; 

    
}


