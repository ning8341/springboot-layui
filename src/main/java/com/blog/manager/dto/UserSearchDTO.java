package com.blog.manager.dto;


import lombok.Data;

/**
 * @Title: UserSearchDTO
 * @Description:
 * @author: dongwn
 * @version: 1.0
 */
@Data
public class UserSearchDTO {
    private String sysUserName;

    private String userPhone;

    private String startTime;

    private String endTime;

}
