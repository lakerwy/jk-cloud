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
import vip.xiaonuo.sys.modular.hr.jkAttendance.entity.HrAttendanceMachine;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachineAddParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachineEditParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachineIdParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachinePageParam;

import java.util.List;

/**
 * 打卡机信息表Service接口
 *
 * @author ljy
 * @date  2023/09/08 14:39
 **/
public interface HrAttendanceMachineService extends IService<HrAttendanceMachine> {

    /**
     * 获取打卡机信息表分页
     *
     * @author ljy
     * @date  2023/09/08 14:39
     */
    Page<HrAttendanceMachine> page(HrAttendanceMachinePageParam hrAttendanceMachinePageParam);

    /**
     * 添加打卡机信息表
     *
     * @author ljy
     * @date  2023/09/08 14:39
     */
    void add(HrAttendanceMachineAddParam hrAttendanceMachineAddParam);

    /**
     * 编辑打卡机信息表
     *
     * @author ljy
     * @date  2023/09/08 14:39
     */
    void edit(HrAttendanceMachineEditParam hrAttendanceMachineEditParam);

    /**
     * 删除打卡机信息表
     *
     * @author ljy
     * @date  2023/09/08 14:39
     */
    void delete(List<HrAttendanceMachineIdParam> hrAttendanceMachineIdParamList);

    /**
     * 获取打卡机信息表详情
     *
     * @author ljy
     * @date  2023/09/08 14:39
     */
    HrAttendanceMachine detail(HrAttendanceMachineIdParam hrAttendanceMachineIdParam);

    /**
     * 获取打卡机信息表详情
     *
     * @author ljy
     * @date  2023/09/08 14:39
     **/
    HrAttendanceMachine queryEntity(String id);
}
