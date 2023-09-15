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
package vip.xiaonuo.sys.modular.hr.userAttendance.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.AttendanceTimeSlot;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.HrUserAttendance;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.UserAttendanceCountEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.UserAttendanceDayInfoEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendanceIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendancePageParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 打卡记录表Service接口
 *
 * @author ljy
 * @date  2023/09/07 14:24
 **/
public interface HrUserAttendanceService extends IService<HrUserAttendance> {

    /**
     * 获取打卡记录表分页
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    Page<HrUserAttendance> page(HrUserAttendancePageParam hrUserAttendancePageParam);

    /**
     * 添加打卡记录表
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    void add(HrUserAttendanceAddParam hrUserAttendanceAddParam);


    /**
     * 添加打卡记录表
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    String attendance(HrUserAttendanceAddParam hrUserAttendanceAddParam);

    /**
     * 编辑打卡记录表
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    void edit(HrUserAttendanceEditParam hrUserAttendanceEditParam);

    /**
     * 删除打卡记录表
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    void delete(List<HrUserAttendanceIdParam> hrUserAttendanceIdParamList);

    /**
     * 获取打卡记录表详情
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    HrUserAttendance detail(HrUserAttendanceIdParam hrUserAttendanceIdParam);

    /**
     * 获取打卡记录表详情
     *
     * @author ljy
     * @date  2023/09/07 14:24
     **/
    HrUserAttendance queryEntity(String id);

    /**
     *  根据人员ID获取打卡配置，处理班次配置缓存
     *
     * @author ljy
     * @date  2023/09/13 15:24
     **/
    List<AttendanceTimeSlot> getAttendanceConfigValueByKey(Date dateNow);


    /**
     * 用戶打卡月统计接口
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    List<UserAttendanceCountEntity> attendanceMonthCount();

    /**
     * 用戶每天打卡详情
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    List<HrUserAttendance> attendanceDayInfo(HrUserAttendancePageParam hrUserAttendancePageParam);
}
