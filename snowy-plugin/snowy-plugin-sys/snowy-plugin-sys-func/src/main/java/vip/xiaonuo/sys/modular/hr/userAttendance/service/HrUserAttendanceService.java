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
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.HrUserAttendance;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.*;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.AttendanceTimeSlot;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceCountEntity;

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
     *  数据缓存
     *
     * @author ljy
     * @date  2023/10/10 11:23
     **/
    void attendanceConfigCache();

    /**
     * 添加补卡记录
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    void resubmitAttendance(String processInstanceId, String applyType);

    /**
     * 打卡记录同步考勤统计表
     * @author ljy
     * @date  2023/09/26 14:24
     */
    void attendanceStatJobHandle();

    /**
     * 递归该部门下面有多少人员
     * author ljy
     * @date  2023/10/09 16:24
     * */
    String getOrgAndUserId(String orgId);

    //获取用户考勤配置-优先取缓存

    /**
     * 获取用户考勤配置-优先取缓存
     * author ljy
     * @date  2023/10/16 11:24
     * */
    List<AttendanceTimeSlot> getAttendanceTimeSlotList(String userId, Date dateNow);
    List<AttendanceTimeSlot> userConfigCache(ConfigCacheListParam configCacheListParam);
}
