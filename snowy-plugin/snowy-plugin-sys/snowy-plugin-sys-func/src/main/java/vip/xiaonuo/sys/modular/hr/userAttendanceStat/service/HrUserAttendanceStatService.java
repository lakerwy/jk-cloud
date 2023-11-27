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
package vip.xiaonuo.sys.modular.hr.userAttendanceStat.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.atdData.entity.OriginalAtdData;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.HrUserAttendance;
import vip.xiaonuo.sys.modular.hr.userAttendance.param.HrUserAttendancePageParam;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceConfigEntity;
import vip.xiaonuo.sys.modular.hr.userAttendance.vo.UserAttendanceCountEntity;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStat;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.param.*;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤统计表Service接口
 *
 * @author ljy
 * @date  2023/10/07 10:28
 **/
public interface HrUserAttendanceStatService extends IService<HrUserAttendanceStat> {

    /**
     * 获取考勤统计表分页
     *
     * @author ljy
     * @date  2023/10/07 10:28
     */
    Page<HrUserAttendanceStat> page(HrUserAttendanceStatPageParam hrUserAttendanceStatPageParam);

    /**
     * 添加考勤统计表
     *
     * @author ljy
     * @date  2023/10/07 10:28
     */
    void add(HrUserAttendanceStatAddParam hrUserAttendanceStatAddParam);

    /**
     * 编辑考勤统计表
     *
     * @author ljy
     * @date  2023/10/07 10:28
     */
    void edit(HrUserAttendanceStatEditParam hrUserAttendanceStatEditParam);

    /**
     * 删除考勤统计表
     *
     * @author ljy
     * @date  2023/10/07 10:28
     */
    void delete(List<HrUserAttendanceStatIdParam> hrUserAttendanceStatIdParamList);

    /**
     * 获取考勤统计表详情
     *
     * @author ljy
     * @date  2023/10/07 10:28
     */
    HrUserAttendanceStat detail(HrUserAttendanceStatIdParam hrUserAttendanceStatIdParam);

    /**
     * 获取考勤统计表详情
     *
     * @author ljy
     * @date  2023/10/07 10:28
     **/
    HrUserAttendanceStat queryEntity(String id);

    /**
     * 考勤记录统计handle处理
     *
     * @author ljy
     * @date  2023/10/07 10:28
     **/
    void handleAttendanceEvent(HrUserAttendanceStatEvent hrUserAttendanceStatEvent);

    /**
     * 获取考勤统计天数据
     *
     * @author ljy
     * @date  2023/10/10 16:28
     */
    Map<String,Object> dayStatInfo();

    /**
     * 获取打卡配置数据
     *
     * @author ljy
     * @date  2023/10/11 22:28
     */
    List<UserAttendanceConfigEntity> getUserConfigCachData(String userId, Date date);

    /**
     * 用戶每天打卡详情
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    Map<String,List<HrUserAttendance>> attendanceDayInfo(HrUserAttendancePageParam hrUserAttendancePageParam);

    /**
     * 用戶打卡月统计接口
     *
     * @author ljy
     * @date  2023/09/07 14:24
     */
    List<UserAttendanceCountEntity> attendanceMonthCount(HrUserAttendancePageParam hrUserAttendancePageParam);

    AttendanceStatNumData attendanceDayPreview();

    /**
     * 用户考勤概览每日统计接口
     *
     * @author ljy
     * @date  2023/10/18 14:24
     * */
    LinkedHashMap<Integer, String> statPreviewDateList(AttendanceDayStatParam attendanceDayStatParam);

    /**
     * 用户考勤概览月统计接口
     *
     * @author ljy
     * @date  2023/10/18 14:24
     * */
    LinkedHashMap<Integer, String> statPreviewMonthList(AttendanceDayStatParam attendanceDayStatParam);

    /**
     * 用户考勤概览每日统计接口
     *
     * @author ljy
     * @date  2023/10/16 16:24
     * */
    Page<AttendanceDayStatParam> dayStatPreview(AttendanceDayStatParam attendanceDayStatParam);

    /**
     * 用户考勤概览 -- 月统计接口
     *
     * @author ljy
     * @date  2023/10/17 16:24
     * */
    Page<MonthStatData> monthStatPreview(AttendanceMonthStatParam attendanceMonthStatParam);

    void downloadMonthExcel(AttendanceMonthStatParam attendanceMonthStatParam, HttpServletResponse response) throws Exception;

    /** 判断当前的时间是否为节假日或者是加班日,type = 1 是否法定节假日，type = 2 是否法定加班日  */
    Boolean isFestival(String date,Integer type);

    /**
     * 月统计考勤概览-日考勤数据汇总接口
     *
     * @author ljy
     * @date  2023/10/16 16:24
     * */
    Page<MonthPreDayStatData> monthPreDayStat(MonthPreDayStatParam monthPreDayStatParam);

    /**
    * 下载打卡时间excel数据
    * */
    void downloadExcel(DayStatExcelParam dayStatExcelParam, HttpServletResponse response) throws Exception;

    //void workbookExcelDay(List<DayStatExcelParam> dayStatExcelList,HttpServletResponse response) throws Exception;

    /** 月统计考勤概览-假勤分析*/
    AttendanceAnalysisData attendanceAnalysis(AttendanceAnalysisParam attendanceAnalysisParam);

    Page<OriginalAtdData> originalAtdData(OriginalAtdParam originalAtdParam);

    Map<String,UserAtdConfigMap> getUserAtdConfigMap();
}
