package com.bookkeeping.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author dongwn
 * @since 2020-07-21
 */
@Data
@TableName("t_standing")
public class StandingModel {


    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @TableField(value = "real_in")
    private Double realIn;

    @TableField(value = "real_out")
    private Double realOut;

    @TableField(value = "total")
    private Double total;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "is_deleted")
    private Integer isDeleted;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(value = "bill_time")
    private Date billTime;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "create_by")
    private String createBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "update_by")
    private String updateBy;


}


