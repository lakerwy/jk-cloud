package vip.xiaonuo.oa.modular.attendance.apply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.apply.entity.HolidayApply;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyAddParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyEditParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyIdParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyPageParam;

import java.util.List;

/**
 * 请假管理表Service接口
 *
 * @author mayike
 * @date  2024/07/09 12:00
 **/
public interface HolidayApplyService extends IService<HolidayApply> {

    /**
     * 获取请假管理表分页
     *
     * @author mayike
     * @date  2024/07/09 12:00
     */
    Page<HolidayApply> page(HolidayApplyPageParam holidayApplyPageParam);

    /**
     * 添加请假管理表
     *
     * @author mayike
     * @date  2024/07/09 12:00
     */
    void add(HolidayApplyAddParam holidayApplyAddParam);

    /**
     * 编辑请假管理表
     *
     * @author mayike
     * @date  2024/07/09 12:00
     */
    void edit(HolidayApplyEditParam holidayApplyEditParam);

    /**
     * 删除请假管理表
     *
     * @author mayike
     * @date  2024/07/09 12:00
     */
    void delete(List<HolidayApplyIdParam> holidayApplyIdParamList);

    /**
     * 获取请假管理表详情
     *
     * @author mayike
     * @date  2024/07/09 12:00
     */
    HolidayApply detail(HolidayApplyIdParam holidayApplyIdParam);

    /**
     * 获取请假管理表详情
     *
     * @author mayike
     * @date  2024/07/09 12:00
     **/
    HolidayApply queryEntity(String id);
}
