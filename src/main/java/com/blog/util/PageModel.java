package com.blog.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Data;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

/**
 * 分页实体类
 *
 * @author dongwn
 */
@Data
public class PageModel<T> extends Page<T> {

    private static final long serialVersionUID = 1L;
    private int page;
    private int pageSize;
    private int startNum;
    private boolean allowPage;

    public PageModel(int current, int size) {
        super(current, size);
    }

    public PageModel(int current, int size, String orderByField) {
        super(current, size);
    }

    private static int defaultPage = 1;
    private static int defaultPageSize = 10;


    public PageModel() {
        page = defaultPage;
        pageSize = defaultPageSize;
        allowPage = true;
    }

    public PageModel(HttpServletRequest request) {
        Map<String, Object> res = getAllRequestParam(request);
        // 设置默认分页页数
        if (!StringUtils.isEmpty(res.get("pageNum"))) {
            page = Convert.toInt(res.get("pageNum"));
            res.remove("pageNum");
            // 设置默认分页每页行数
            if (StringUtils.isEmpty(res.get("pageSize"))) {
                pageSize = defaultPageSize;
            } else {
                pageSize = Convert.toInt(res.get("pageSize"));
                res.remove("pageSize");
            }
            startNum = (page - 1) * pageSize;
        } else {
            page = defaultPage;
            pageSize = defaultPageSize;
        }
        // 适配 page=
        if (StrUtil.isEmpty(request.getParameter("pageNum"))) {
            page = 1;
            allowPage = true;
        } else if (Convert.toInt(request.getParameter("pageNum")) == 0) { // 适配 page=0
            allowPage = false;// 不是分页
        } else if (StrUtil.isNotEmpty(request.getParameter("pageNum"))
                && Convert.toInt(request.getParameter("pageNum")) != 0) {
            page = Convert.toInt(request.getParameter("pageNum"));
            allowPage = true;
        }
        // 兼容分页既设置mybatisplus分页插件的起始页以及limit的第二个参数size
        super.setCurrent(page);
        super.setSize(pageSize);
    }

    public static Map<String, Object> getAllRequestParam(final HttpServletRequest request) {
        Map<String, Object> res = new HashMap<String, Object>();
        Enumeration<?> temp = request.getParameterNames();
        if (null == temp) {
            return res;
        }
        while (temp.hasMoreElements()) {
            String en = (String) temp.nextElement();
            String value = request.getParameter(en);
            res.put(en, value);
            // 如果字段的值为空，判断若值为空，则删除这个字段>
            if (StringUtils.isEmpty(res.get(en))) {
                res.remove(en);
            }
        }
        return res;
    }


}
