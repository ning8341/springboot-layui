package com.bookkeeping.controller;


import cn.hutool.core.convert.Convert;
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
import java.util.stream.Collectors;


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
        Double cashIn=0.0;
        Double cardIn=0.0;
        Double unionpayIn=0.0;
        Double publicIn=0.0;
        Double elmIn=0.0;
        Double meituanIn=0.0;
        Double total=0.0;
        Double cashOut=0.0;
        Double cardOut=0.0;
        Double tatalIn=0.0;
        Double tatalOut=0.0;
       for(StandingModel standingModel:standingModelList){
           if(!StringUtils.isEmpty(standingModel.getCashIn())){
               cashIn+=standingModel.getCashIn();
           }
           if(!StringUtils.isEmpty(standingModel.getCardIn())){
               cardIn+=standingModel.getCardIn();
           }
           if(!StringUtils.isEmpty(standingModel.getUnionpayIn())){
               unionpayIn+=standingModel.getUnionpayIn();
           }
           if(!StringUtils.isEmpty(standingModel.getPublicIn())){
               publicIn+=standingModel.getPublicIn();
           }
           if(!StringUtils.isEmpty(standingModel.getElmIn())){
               elmIn+=standingModel.getElmIn();
           }
           if(!StringUtils.isEmpty(standingModel.getMeituanIn())){
               meituanIn+=standingModel.getMeituanIn();
           }
           if(!StringUtils.isEmpty(standingModel.getCashOut())){
               cashOut+=standingModel.getCashOut();
           }
           if(!StringUtils.isEmpty(standingModel.getCardOut())){
               cardOut+=standingModel.getCardOut();
           }
       }
        tatalIn=cashIn+cardIn+unionpayIn+publicIn+elmIn+meituanIn;
        tatalOut=cashOut+cardOut;
        total =tatalIn-tatalOut;
        resMap.put("total",total);
        resMap.put("tatalOut",tatalOut);
        resMap.put("tatalIn",tatalIn);
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
        double cashIn = !StringUtils.isEmpty(standingModel.getCashIn()) ? Convert.toDouble(standingModel.getCashIn()) : 0.0;
        double cashOut = !StringUtils.isEmpty(standingModel.getCashOut()) ? Convert.toDouble(standingModel.getCashOut()) : 0.0;
        double cardIn = !StringUtils.isEmpty(standingModel.getCardIn()) ? Convert.toDouble(standingModel.getCardIn()) : 0.0;
        double cardOut = !StringUtils.isEmpty(standingModel.getCardOut()) ? Convert.toDouble(standingModel.getCardOut()) : 0.0;
        double unionpayIn = !StringUtils.isEmpty(standingModel.getUnionpayIn()) ? Convert.toDouble(standingModel.getUnionpayIn()) : 0.0;
        double publicIn = !StringUtils.isEmpty(standingModel.getPublicIn()) ? Convert.toDouble(standingModel.getPublicIn()) : 0.0;
        double elmIn = !StringUtils.isEmpty(standingModel.getElmIn()) ? Convert.toDouble(standingModel.getElmIn()) : 0.0;
        double meituanIn = !StringUtils.isEmpty(standingModel.getMeituanIn()) ? Convert.toDouble(standingModel.getMeituanIn()) : 0.0;
        double total = cashIn - cashOut + cardIn - cardOut + unionpayIn + publicIn + elmIn + meituanIn;
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


}

