package com.jwt.util;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Data
public class PageModel<T> extends Page<T> {

    private int page;
    private int pageSize;
    private Map<String, Object> paramMap;

    public PageModel(HttpServletRequest request) {
        page = 1;
        pageSize = 10;
        Map<String, Object> res = getAllRequestParam(request);
        // 兼容分页既设置mybatisplus分页插件的起始页以及limit的第二个参数size
        if (!StringUtils.isEmpty(res.get("page"))) {
            page = Convert.toInt(res.get("page"));
        }
        if (!StringUtils.isEmpty(res.get("limit"))) {
            pageSize = Convert.toInt(res.get("limit"));
        }
        super.setCurrent(page);
        super.setSize(pageSize);
        paramMap = res;
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
