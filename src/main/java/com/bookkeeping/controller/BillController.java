package com.bookkeeping.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.bookkeeping.entity.BillModel;
import com.bookkeeping.entity.UserModel;
import com.bookkeeping.service.BillService;
import com.bookkeeping.util.PageModel;
import com.bookkeeping.util.Result;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;

    /**
     * 跳转博客相关操作页面
     *
     * @return
     */
    @GetMapping("/billList")
    public String toBlogPage() {
        return "bill/billList";
    }


    @PostMapping("/billList")
    @ResponseBody
    public Map<String, Object> list(@RequestBody PageModel<BillModel> pageModel) {
        pageModel = (PageModel<BillModel>) billService.findPage(pageModel);
        return Result.ok(pageModel);
    }

    @PostMapping("/save")
    @ResponseBody
    public Map<String, Object> saveOrUpdate(@RequestBody BillModel billModel) {
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        billModel.setId(UUID.randomUUID().toString());
        billModel.setCreateTime(new Date());
        billModel.setCreateBy(user.getId());
        billModel.setTitle(DateUtil.format(billModel.getBillTime(), "yyyy-MM-dd hh:mm:ss") + "的订单");
        billService.save(billModel);
        return Result.ok("操作成功!");
    }

    @DeleteMapping("/delBatchByIds")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody String[] ids) {
        List<String> idList = Arrays.asList(ids);
        UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
        List<BillModel> billModelList = new ArrayList<>();
        for (String id : idList) {
            BillModel billModel = new BillModel();
            billModel.setId(id);
            billModel.setIsDeleted(1);
            billModel.setUpdateBy(user.getId());
            billModel.setUpdateTime(new Date());
            billModelList.add(billModel);
        }
        billService.updateBatchById(billModelList);
        return Result.ok("操作成功");
    }


}
