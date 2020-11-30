package com.jwt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户表
 */
@Data
@TableName("t_user")
public class UserModel {

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "real_name")
    private String realName;

    @TableField(value = "email")
    private String email;

    @TableField(value = "level")
    private String level;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "sex")
    private Integer sex;

    @TableField(value = "pass_word")
    private String passWord;

    @TableField(value = "salt")
    private String salt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "login_at")
    private Date loginAt;

    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(value = "created_by")
    private String createdBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "updated_by")
    private String updatedBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "updated_at")
    private Date updatedAt;

}
