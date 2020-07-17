package com.bookkeeping.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bookkeeping.entity.BillModel;
import com.bookkeeping.entity.ConsumModel;
import com.bookkeeping.entity.UserModel;
import com.bookkeeping.service.BillService;
import com.bookkeeping.service.ConsumService;
import com.bookkeeping.service.UserService;
import com.bookkeeping.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private BillService billService;

    @Autowired
    private ConsumService consumService;

    @GetMapping("/getInfo")
    @ResponseBody
    public Map<String, Object> getInfo() {
        Map<String, Object> dataMap = new ConcurrentHashMap<>();
        QueryWrapper<UserModel> wrapper = new QueryWrapper<UserModel>();
        wrapper.lambda().eq(UserModel::getIsDeleted, 0);
        int userNum = userService.list(wrapper).size();
        QueryWrapper<BillModel> billWrapper = new QueryWrapper<BillModel>();
        billWrapper.lambda().eq(BillModel::getIsDeleted, 0);
        int billNum = billService.list(billWrapper).size();
        QueryWrapper<ConsumModel> consumWrapper = new QueryWrapper<ConsumModel>();
        consumWrapper.lambda().eq(ConsumModel::getIsDeleted, 0);
        int consumNum = consumService.list(consumWrapper).size();
        dataMap.put("userNum", userNum);
        dataMap.put("billNum", billNum);
        dataMap.put("consumNum", consumNum);
        return Result.ok(dataMap);
    }
}
