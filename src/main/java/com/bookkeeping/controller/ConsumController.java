package com.bookkeeping.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.bookkeeping.entity.ConsumModel;
import com.bookkeeping.entity.UserModel;
import com.bookkeeping.service.ConsumService;
import com.bookkeeping.util.PageModel;
import com.bookkeeping.util.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dongwn
 * @since 2020-07-17
 */

@Controller
@RequestMapping("/consum")
public class ConsumController {
    @Autowired
    private ConsumService consumService;

    @GetMapping("/consumList")
    public String toBlogPage() {
        return "consum/consumList";
    }

    @PostMapping("/consumList")
    @ResponseBody
    public Map<String, Object> list(@RequestBody PageModel<ConsumModel> pageModel) {
        pageModel = (PageModel<ConsumModel>) consumService.findPage(pageModel);
        return Result.ok(pageModel);
    }

    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> saveOrUpdate(@RequestBody ConsumModel consumModel) {
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        consumModel.setId(UUID.randomUUID().toString());
        consumModel.setCreateTime(new Date());
        consumModel.setCreateBy(user.getId());
        consumModel.setTitle(DateUtil.format(consumModel.getConsumTime(), "yyyy-MM-dd hh:mm:ss") + "的费用支出");
        consumService.save(consumModel);
        return Result.ok("操作成功!");
    }


    @DeleteMapping("/delBatchByIds")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody String[] ids) {
        List<String> idList = Arrays.asList(ids);
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        List<ConsumModel> consumModelList = new ArrayList<>();
        for (String id : idList) {
            ConsumModel consumModel = new ConsumModel();
            consumModel.setId(id);
            consumModel.setIsDeleted(1);
            consumModel.setUpdateBy(user.getId());
            consumModel.setUpdateTime(new Date());
            consumModelList.add(consumModel);
        }
        consumService.updateBatchById(consumModelList);
        return Result.ok("操作成功");
    }


}

