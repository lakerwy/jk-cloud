/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.oa.modular.attendance.shift.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Calendar;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarAddParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarEditParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarPageParam;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Calendar;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarAddParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarEditParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarIdParam;
import vip.xiaonuo.oa.modular.attendance.shift.param.CalendarPageParam;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Calendar;
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

    /**
     * 编辑日历表
     *
     * @author mayike
     * @date  2024/06/15 12:59
     */
    void edit(CalendarEditParam calendarEditParam);

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
}
