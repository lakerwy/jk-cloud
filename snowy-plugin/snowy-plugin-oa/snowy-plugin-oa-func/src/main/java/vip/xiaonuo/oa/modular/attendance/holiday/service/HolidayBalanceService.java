package vip.xiaonuo.oa.modular.attendance.holiday.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.holiday.entity.HolidayBalance;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayBalanceAddParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayBalanceEditParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayBalanceIdParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayBalancePageParam;
import vip.xiaonuo.oa.modular.attendance.holiday.vo.HolidayVo;

import java.util.List;

/**
 * 假期余额管理表Service接口
 *
 * @author mayike
 * @date  2024/07/05 10:57
 **/
public interface HolidayBalanceService extends IService<HolidayBalance> {

    /**
     * 获取假期余额管理表分页
     *
     * @author mayike
     * @date  2024/07/05 10:57
     */
    Page<HolidayVo> page(HolidayBalancePageParam holidayBalancePageParam);

    /**
     * 添加假期余额管理表
     *
     * @author mayike
     * @date  2024/07/05 10:57
     */
    void add(HolidayBalanceAddParam holidayBalanceAddParam);

    /**
     * 编辑假期余额管理表
     *
     * @author mayike
     * @date  2024/07/05 10:57
     */
    void edit(HolidayBalanceEditParam holidayBalanceEditParam);

    /**
     * 删除假期余额管理表
     *
     * @author mayike
     * @date  2024/07/05 10:57
     */
    void delete(List<HolidayBalanceIdParam> holidayBalanceIdParamList);

    /**
     * 获取假期余额管理表详情
     *
     * @author mayike
     * @date  2024/07/05 10:57
     */
    HolidayBalance detail(HolidayBalanceIdParam holidayBalanceIdParam);

    /**
     * 获取假期余额管理表详情
     *
     * @author mayike
     * @date  2024/07/05 10:57
     **/
    HolidayBalance queryEntity(String id);

    /**
     * 新假期时，给每个用户分配假期数据
     * @param id
     */
    void initUserHolidayData(int year,String id,List<String> userIds);

    void initData();

    List<String> getUserIds();

    void deleteByIds(List<String> ids);
}
