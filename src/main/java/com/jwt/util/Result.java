package com.jwt.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 接口返回数据格式
 */
public class Result implements Serializable {

    public static Map<String, Object> ok(String msg) {
        Map<String, Object> resMap = new ConcurrentHashMap();
        resMap.put("status", "OK");
        resMap.put("code", 0);
        resMap.put("msg", msg);
        return resMap;
    }
    public static Map<String, Object> ok(IPage<?> page) {
        Map<String, Object> resMap = new ConcurrentHashMap();
        List<?> dataList = page.getRecords();
        resMap.put("status", "OK");
        resMap.put("code", 0);
        resMap.put("current",page.getCurrent());
        resMap.put("pageSize",page.getSize());
        resMap.put("count", page.getTotal());
        resMap.put("data", dataList);
        return resMap;
    }
    public static Map<String, Object> ok() {
        Map<String, Object> resMap = new ConcurrentHashMap();
        resMap.put("status", "OK");
        resMap.put("code", 0);
        return resMap;
    }

    public static Map<String, Object> error(Object data) {
        Map<String, Object> resMap = new ConcurrentHashMap<String, Object>();
        resMap.put("status", "error");
        resMap.put("code", 1);
        resMap.put("details", data);
        return resMap;
    }


}
