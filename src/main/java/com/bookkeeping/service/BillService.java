package com.bookkeeping.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookkeeping.entity.BillModel;
import com.bookkeeping.util.PageModel;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dongwn
 * @since 2020-06-28
 */
public interface BillService extends IService<BillModel> {
    Page<BillModel> findPage(PageModel<BillModel> pageModel);

}
