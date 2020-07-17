package com.bookkeeping.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookkeeping.entity.ConsumModel;
import com.bookkeeping.util.PageModel;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dongwn
 * @since 2020-07-17
 */
public interface ConsumService extends IService<ConsumModel> {
    Page<ConsumModel> findPage(PageModel<ConsumModel> pageModel);

}
