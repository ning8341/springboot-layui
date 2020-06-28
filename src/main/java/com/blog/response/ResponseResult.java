package com.blog.response;

import com.blog.common.IStatusMessage;
import lombok.Data;

import java.io.Serializable;

/**
 * @Title: ResponseResult
 * @Description: 前端请求响应结果,code:编码,message:描述,obj对象，可以是单个数据对象，数据列表或者PageInfo
 * @author: dongwn
 * @version: 1.0
 */
@Data
public class ResponseResult implements Serializable{

    private String code;
    private String message;
    private Object obj;

    public ResponseResult() {
        this.code = IStatusMessage.SystemStatus.SUCCESS.getCode();
        this.message = IStatusMessage.SystemStatus.SUCCESS.getMessage();
    }

    public ResponseResult(IStatusMessage statusMessage){
        this.code = statusMessage.getCode();
        this.message = statusMessage.getMessage();

    }



}
