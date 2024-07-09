package vip.xiaonuo.oa.modular.attendance.shift.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Calendar;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarAddParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarEditParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarPageParam;

import java.util.List;

/**
 * 日历表Service接口
 *
 * @author mayike
 * @date  2024/06/15 12:59
 **/
public interface CalendarService extends IService<Calendar> {

    /**
     * 获取日历表分页
     *
     * @author mayike
     * @date  2024/06/15 12:59
     */
    Page<Calendar> page(CalendarPageParam calendarPageParam);

    /**
     * 添加日历表
     *
     * @author mayike
     * @date  2024/06/15 12:59
     */
    void add(CalendarAddParam calendarAddParam);

//    /**
//     * 编辑日历表
//     *
//     * @author mayike
//     * @date  2024/06/15 12:59
//     */
//    void edit(CalendarEditParam calendarEditParam);

    /**
     * 删除日历表
     *
     * @author mayike
     * @date  2024/06/15 12:59
     */
    void delete(List<CalendarIdParam> calendarIdParamList);

    /**
     * 获取日历表详情
     *
     * @author mayike
     * @date  2024/06/15 12:59
     */
    Calendar detail(CalendarIdParam calendarIdParam);

    /**
     * 获取日历表详情
     *
     * @author mayike
     * @date  2024/06/15 12:59
     **/
    Calendar queryEntity(String id);

    @Transactional(rollbackFor = Exception.class)
    void generateAndSaveCalendar(int year);

    void updateCalendar(CalendarEditParam calendarEditParam);
}
