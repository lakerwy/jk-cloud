package vip.xiaonuo.oa.modular.attendance.holiday.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.oa.modular.attendance.holiday.entity.HolidayBalance;
import vip.xiaonuo.oa.modular.attendance.holiday.vo.HolidayVo;

import java.util.List;

/**
 * 假期余额管理表Mapper接口
 *
 * @author mayike
 * @date  2024/07/05 10:57
 **/
public interface HolidayBalanceMapper extends BaseMapper<HolidayBalance> {
    Page<HolidayVo> getUserHolidays(Page<HolidayBalance> page, @Param(Constants.WRAPPER) Wrapper<HolidayBalance> queryWrapper);
}
