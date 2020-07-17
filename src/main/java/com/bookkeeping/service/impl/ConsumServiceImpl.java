package com.bookkeeping.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookkeeping.entity.ConsumModel;
import com.bookkeeping.mapper.ConsumMapper;
import com.bookkeeping.service.ConsumService;
import com.bookkeeping.util.PageModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dongwn
 * @since 2020-07-17
 */
@Service
public class ConsumServiceImpl extends ServiceImpl<ConsumMapper, ConsumModel> implements ConsumService {

    @Override
    public Page<ConsumModel> findPage(PageModel<ConsumModel> pageModel) {
        QueryWrapper<ConsumModel> wrapper = commonWrapper(pageModel);
        return (Page<ConsumModel>) super.page(pageModel, wrapper);
    }

    private QueryWrapper<ConsumModel> commonWrapper(PageModel<ConsumModel> pageModel) {
        Map paramMap = pageModel.getCondition();
        QueryWrapper<ConsumModel> wrapper = new QueryWrapper<ConsumModel>();
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
