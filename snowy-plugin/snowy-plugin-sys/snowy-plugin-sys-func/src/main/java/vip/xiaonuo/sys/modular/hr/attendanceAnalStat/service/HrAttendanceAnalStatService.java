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
package vip.xiaonuo.sys.modular.hr.attendanceAnalStat.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.entity.HrAttendanceAnalStat;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatAddParam;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatEditParam;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatIdParam;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatPageParam;

import java.util.List;

/**
 * 考勤-员工考勤统计分析数据汇总表Service接口
 *
 * @author ljy
 * @date  2023/11/04 08:35
 **/
public interface HrAttendanceAnalStatService extends IService<HrAttendanceAnalStat> {

    /**
     * 获取考勤-员工考勤统计分析数据汇总表分页
     *
     * @author ljy
     * @date  2023/11/04 08:35
     */
    Page<HrAttendanceAnalStat> page(HrAttendanceAnalStatPageParam hrAttendanceAnalStatPageParam);

    /**
     * 添加考勤-员工考勤统计分析数据汇总表
     *
     * @author ljy
     * @date  2023/11/04 08:35
     */
    void add(HrAttendanceAnalStatAddParam hrAttendanceAnalStatAddParam);

    /**
     * 编辑考勤-员工考勤统计分析数据汇总表
     *
     * @author ljy
     * @date  2023/11/04 08:35
     */
    void edit(HrAttendanceAnalStatEditParam hrAttendanceAnalStatEditParam);

    /**
     * 删除考勤-员工考勤统计分析数据汇总表
     *
     * @author ljy
     * @date  2023/11/04 08:35
     */
    void delete(List<HrAttendanceAnalStatIdParam> hrAttendanceAnalStatIdParamList);

    /**
     * 获取考勤-员工考勤统计分析数据汇总表详情
     *
     * @author ljy
     * @date  2023/11/04 08:35
     */
    HrAttendanceAnalStat detail(HrAttendanceAnalStatIdParam hrAttendanceAnalStatIdParam);

    /**
     * 获取考勤-员工考勤统计分析数据汇总表详情
     *
     * @author ljy
     * @date  2023/11/04 08:35
     **/
    HrAttendanceAnalStat queryEntity(String id);
}
