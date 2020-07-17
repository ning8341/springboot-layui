package com.bookkeeping.controller;


import com.bookkeeping.entity.BillModel;
import com.bookkeeping.service.BillService;
import com.bookkeeping.util.PageModel;
import com.bookkeeping.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService articleService;

    /**
     * 跳转博客相关操作页面
     *
     * @return
     */
    @GetMapping("/billList")
    public String toBlogPage() {
        return "/bill/billList";
    }


    @PostMapping("/billList")
    @ResponseBody
    public Map<String, Object> list(@RequestBody PageModel<BillModel> pageModel) {
        pageModel = (PageModel<BillModel>) articleService.findPage(pageModel);
        return Result.ok(pageModel);
    }





}
