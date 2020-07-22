package com.bookkeeping.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookkeeping.entity.UserModel;
import com.bookkeeping.service.UserService;
import com.bookkeeping.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private UserService userService;



    @GetMapping("/getInfo")
    @ResponseBody
    public Map<String, Object> getInfo() {
        Map<String, Object> dataMap = new ConcurrentHashMap<>();

        dataMap.put("userNum", 0);
        dataMap.put("billNum", 0);
        dataMap.put("consumNum", 0);
        return Result.ok(dataMap);
    }
}
