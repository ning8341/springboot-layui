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

    @TableField(value = "cash_in")
    private Double cashIn;

    @TableField(value = "cash_out")
    private Double cashOut;

    @TableField(value = "card_in")
    private Double cardIn;

    @TableField(value = "card_out")
    private Double cardOut;

    @TableField(value = "unionpay_in")
    private Double unionpayIn;

    @TableField(value = "public_in")
    private Double publicIn;

    @TableField(value = "elm_in")
    private Double elmIn;

    @TableField(value = "meituan_in")
    private Double meituanIn;

    @TableField(value = "total")
    private Double total;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "is_deleted")
    private Integer isDeleted;

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


