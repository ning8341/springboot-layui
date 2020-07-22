package com.bookkeeping.service.impl;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookkeeping.entity.StandingModel;
import com.bookkeeping.mapper.StandingMapper;
import com.bookkeeping.service.StandingService;
import com.bookkeeping.util.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dongwn
 * @since 2020-07-21
 */
@Service
public class StandingServiceImpl extends ServiceImpl<StandingMapper, StandingModel> implements StandingService {
    @Autowired
    private StandingMapper standingMapper;

    @Override
    public Page<StandingModel> findPage(PageModel<StandingModel> pageModel) {
        QueryWrapper<StandingModel> wrapper = commonWrapper(pageModel);
        return (Page<StandingModel>) super.page(pageModel, wrapper);
    }

    @Override
    public List<Map<String, Object>> queryData(int year) {
        return standingMapper.selectDate(year);
    }


    private QueryWrapper<StandingModel> commonWrapper(PageModel<StandingModel> pageModel) {
        Map paramMap = pageModel.getCondition();
        QueryWrapper<StandingModel> wrapper = new QueryWrapper<StandingModel>();
        wrapper.eq("is_deleted", 0);
        wrapper.orderByDesc("create_time");
        paramMap.forEach((k, v) -> {
            if (!StringUtils.isEmpty(v)) {
                if ("start".equals(k)) {
                    String startDate = Convert.toStr(v) + " 00:00:00";
                    Date start = DateUtil.parse(startDate);
                    wrapper.ge("bill_time", start);
                }
                if ("end".equals(k)) {
                    String endDate = Convert.toStr(v) + " 23:59:59";
                    Date end = DateUtil.parse(endDate);
                    wrapper.le("bill_time", end);
                }
            }
        });
        return wrapper;
    }
}
