package com.bookkeeping.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookkeeping.mapper.BillMapper;
import com.bookkeeping.entity.BillModel;
import com.bookkeeping.service.BillService;
import com.bookkeeping.util.PageModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, BillModel> implements BillService {

    @Override
    public Page<BillModel> findPage(PageModel<BillModel> pageModel) {
        QueryWrapper<BillModel> wrapper = commonWrapper(pageModel);
        return (Page<BillModel>) super.page(pageModel, wrapper);
    }




    private QueryWrapper<BillModel> commonWrapper(PageModel<BillModel> pageModel) {
        Map paramMap = pageModel.getCondition();
        QueryWrapper<BillModel> wrapper = new QueryWrapper<BillModel>();
        wrapper.eq("is_deleted", 0);
        String name = "title";
        paramMap.forEach((k, v) -> {
            if (!StringUtils.isEmpty(v)) {
                if (name.equals(k)) {
                    wrapper.like("title", v);
                }
            }
        });
        return wrapper;
    }
}
