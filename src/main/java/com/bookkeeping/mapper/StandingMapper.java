package com.bookkeeping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookkeeping.entity.StandingModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dongwn
 * @since 2020-07-21
 */
public interface StandingMapper extends BaseMapper<StandingModel> {

    List<Map<String, Object>> selectDate(@Param("year") int year);

}
