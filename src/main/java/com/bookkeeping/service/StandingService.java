package com.bookkeeping.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookkeeping.entity.StandingModel;
import com.bookkeeping.util.PageModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author dongwn
 * @since 2020-07-21
 */
public interface StandingService extends IService<StandingModel> {

    Page<StandingModel> findPage(PageModel<StandingModel> pageModel);

    List<Map<String, Object>> queryData(int year);

}
