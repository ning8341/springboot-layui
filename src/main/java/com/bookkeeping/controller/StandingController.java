package com.bookkeeping.controller;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.bookkeeping.entity.StandingModel;
import com.bookkeeping.entity.UserModel;
import com.bookkeeping.service.StandingService;
import com.bookkeeping.util.PageModel;
import com.bookkeeping.util.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dongwn
 * @since 2020-07-21
 */

@Controller
@RequestMapping("/standing")
public class StandingController {

    @Autowired
    private StandingService standingService;

    /**
     * 跳转博客相关操作页面
     *
     * @return
     */
    @GetMapping("/standingList")
    public String toBlogPage() {
        return "standing/list";
    }


    @PostMapping("/standingList")
    @ResponseBody
    public Map<String, Object> list(@RequestBody PageModel<StandingModel> pageModel) {
        pageModel = (PageModel<StandingModel>) standingService.findPage(pageModel);
        return Result.ok(pageModel);
    }

    @PostMapping("/calculat")
    @ResponseBody
    public Map<String, Object> list(@RequestBody List<StandingModel> standingModelList) {
        Map<String, Object> resMap = new HashMap<>();
        Double realIn = 0.0;
        Double realOut = 0.0;
        for (StandingModel standingModel : standingModelList) {
            if (!StringUtils.isEmpty(standingModel.getRealIn())) {
                realIn += standingModel.getRealIn();
            }
            if (!StringUtils.isEmpty(standingModel.getRealOut())) {
                realOut += standingModel.getRealOut();
            }
        }
        Double total = realIn - realOut;
        resMap.put("total", total);
        resMap.put("tatalOut", realOut);
        resMap.put("tatalIn", realIn);
        return Result.ok(resMap);
    }


    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> saveOrUpdate(@RequestBody StandingModel standingModel) {
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        standingModel.setId(UUID.randomUUID().toString());
        standingModel.setCreateTime(new Date());
        standingModel.setCreateBy(user.getId());
        standingModel.setTotal(getTotal(standingModel));
        standingService.save(standingModel);
        return Result.ok("操作成功!");
    }

    public Double getTotal(StandingModel standingModel) {
        double realIn = !StringUtils.isEmpty(standingModel.getRealIn()) ? Convert.toDouble(standingModel.getRealIn()) : 0.0;
        double realOut = !StringUtils.isEmpty(standingModel.getRealOut()) ? Convert.toDouble(standingModel.getRealOut()) : 0.0;
        double total = realIn - realOut;
        return total;
    }

    @DeleteMapping("/delBatchByIds")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody String[] ids) {
        List<String> idList = Arrays.asList(ids);
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        List<StandingModel> standingModelList = new ArrayList<>();
        for (String id : idList) {
            StandingModel standingModel = new StandingModel();
            standingModel.setId(id);
            standingModel.setIsDeleted(1);
            standingModel.setUpdateBy(user.getId());
            standingModel.setUpdateTime(new Date());
            standingModelList.add(standingModel);
        }
        standingService.updateBatchById(standingModelList);
        return Result.ok("操作成功");
    }

    @GetMapping("/getInfo")
    @ResponseBody
    public Map<String, Object> getInfo() {
        int year = DateUtil.year(new Date());
        Map<String, Object> dataMap = new ConcurrentHashMap<>();
        List<Map<String, Object>> resList = standingService.queryData(year);
        int[] array = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (Map<String, Object> map : resList) {
            int index = Convert.toInt(map.get("MONTH"));
            array[index-1] = Convert.toInt(map.get("number"));
        }
        dataMap.put("year", year);
        dataMap.put("array", array);
        return Result.ok(dataMap);
    }


}

