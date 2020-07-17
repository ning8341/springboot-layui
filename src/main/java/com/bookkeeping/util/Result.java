package com.bookkeeping.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 统一返回工具类
 */
public class Result {

    public static Map<String, Object> ok(String code, String url) {
        Map<String, Object> temp = new ConcurrentHashMap<String, Object>();
        temp.put("code", code);
        temp.put("url", url);
        return temp;
    }

    public static Map<String, Object> ok(String message) {
        Map<String, Object> temp = new ConcurrentHashMap<String, Object>();
        temp.put("code", "1");
        temp.put("msg", message);
        return temp;
    }

    public static Map<String, Object> ok(PageModel pageModel) {
        Map<String, Object> temp = new ConcurrentHashMap<String, Object>();
        temp.put("code", "200");
        temp.put("totals", pageModel.getTotal());
        temp.put("list", pageModel.getRecords());
        return temp;
    }

    public static Map<String, Object> ok(List<?> resList) {
        Map<String, Object> temp = new ConcurrentHashMap<String, Object>();
        temp.put("code", "200");
        temp.put("list", resList);
        return temp;
    }


    public static Map<String, Object> error(String code, String message) {
        Map<String, Object> msgMap = new ConcurrentHashMap<String, Object>();
        msgMap.put("code", code);
        msgMap.put("msg", message);
        return msgMap;
    }

    public static Map<String, Object> ok(Object o) {
        Map<String, Object> msgMap = new ConcurrentHashMap<String, Object>();
        msgMap.put("code", "200");
        msgMap.put("msg", "成功");
        msgMap.put("data", o);
        return msgMap;
    }


}
