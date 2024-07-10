
package vip.xiaonuo.oa.modular.attendance.holiday.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.holiday.entity.HolidayRule;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayRuleAddParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayRuleEditParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayRuleIdParam;
import vip.xiaonuo.oa.modular.attendance.holiday.param.HolidayRulePageParam;

import java.util.List;

/**
 * 假期类型配置表Service接口
 *
 * @author mayike
 * @date  2024/07/05 09:19
 **/
public interface HolidayRuleService extends IService<HolidayRule> {

    /**
     * 获取假期类型配置表分页
     *
     * @author mayike
     * @date  2024/07/05 09:19
     */
    Page<HolidayRule> page(HolidayRulePageParam holidayRulePageParam);

    /**
     * 添加假期类型配置表
     *
     * @author mayike
     * @date  2024/07/05 09:19
     */
    void add(HolidayRuleAddParam holidayRuleAddParam);

    /**
     * 编辑假期类型配置表
     *
     * @author mayike
     * @date  2024/07/05 09:19
     */
    void edit(HolidayRuleEditParam holidayRuleEditParam);

    /**
     * 删除假期类型配置表
     *
     * @author mayike
     * @date  2024/07/05 09:19
     */
    void delete(List<HolidayRuleIdParam> holidayRuleIdParamList);

    /**
     * 获取假期类型配置表详情
     *
     * @author mayike
     * @date  2024/07/05 09:19
     */
    HolidayRule detail(HolidayRuleIdParam holidayRuleIdParam);

    /**
     * 获取假期类型配置表详情
     *
     * @author mayike
     * @date  2024/07/05 09:19
     **/
    HolidayRule queryEntity(String id);

    void initUserHolidayData(int year, String userId);
}
