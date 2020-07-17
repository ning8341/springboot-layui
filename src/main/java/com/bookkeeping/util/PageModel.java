package com.bookkeeping.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页实体类
 *
 * @author dongwn
 */
@Data
public class PageModel<T> extends Page<T> {
    /**
     * 存放条件
     */
    private Map<String, Object> condition = new HashMap<>();

    public PageModel() {
        super();
    }

    public PageModel(int current, int size) {
        super(current, size);
    }

    public PageModel(int current, int size, String orderByField) {
        super(current, size);
    }


}
