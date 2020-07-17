package com.bookkeeping.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * @author dongwn
 * @since 2020-07-17
 */
@Data
@TableName("t_consum")
public class ConsumModel {


    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField(value = "title")
    private String title;

    @TableField(value = "price")
    private Double price;

    @TableField(value = "details")
    private String details;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "consum_time")
    private Date consumTime;

    @TableField(value = "type")
    private Integer type;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "create_by")
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "update_by")
    private String updateBy;






}


