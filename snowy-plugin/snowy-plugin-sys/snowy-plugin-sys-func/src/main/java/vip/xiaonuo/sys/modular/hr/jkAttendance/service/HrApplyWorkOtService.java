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
package vip.xiaonuo.sys.modular.hr.jkAttendance.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.jkAttendance.entity.HrApplyWorkOt;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrApplyWorkOtAddParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrApplyWorkOtEditParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrApplyWorkOtIdParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrApplyWorkOtPageParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.vo.UserWorkOtVo;

import java.util.List;

/**
 * 考勤-申请加班数据表Service接口
 *
 * @author ljy
 * @date  2023/10/29 19:57
 **/
public interface HrApplyWorkOtService extends IService<HrApplyWorkOt> {

    /**
     * 获取考勤-申请加班数据表分页
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    Page<HrApplyWorkOt> page(HrApplyWorkOtPageParam hrApplyWorkOtPageParam);

    /**
     * 添加考勤-申请加班数据表
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    void add(HrApplyWorkOtAddParam hrApplyWorkOtAddParam);

    /**
     * 编辑考勤-申请加班数据表
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    void edit(HrApplyWorkOtEditParam hrApplyWorkOtEditParam);

    /**
     * 删除考勤-申请加班数据表
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    void delete(List<HrApplyWorkOtIdParam> hrApplyWorkOtIdParamList);

    /**
     * 获取考勤-申请加班数据表详情
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    HrApplyWorkOt detail(HrApplyWorkOtIdParam hrApplyWorkOtIdParam);

    /**
     * 获取考勤-申请加班数据表详情
     *
     * @author ljy
     * @date  2023/10/29 19:57
     **/
    HrApplyWorkOt queryEntity(String id);

    /**
     * 获取当前时间该用户的加班时长(可调休最低单位为半天[4小时]，不足4小时不计算，且减去21.5小时的基础时间)
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    Page<UserWorkOtVo> userWorkOtVoPage(UserWorkOtVo userWorkOtVo);

    Double countWorkHours(String beginTime, String endTime);
}
