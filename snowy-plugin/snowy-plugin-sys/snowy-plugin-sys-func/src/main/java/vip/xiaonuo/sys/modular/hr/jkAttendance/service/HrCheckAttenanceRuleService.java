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
import vip.xiaonuo.sys.modular.hr.jkAttendance.entity.HrCheckAttenanceRule;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrCheckAttenanceRuleAddParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrCheckAttenanceRuleEditParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrCheckAttenanceRuleIdParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrCheckAttenanceRulePageParam;

import java.util.List;

/**
 * 考勤-考勤以及申请类的规则表数据Service接口
 *
 * @author ljy
 * @date  2023/10/29 20:00
 **/
public interface HrCheckAttenanceRuleService extends IService<HrCheckAttenanceRule> {

    /**
     * 获取考勤-考勤以及申请类的规则表数据分页
     *
     * @author ljy
     * @date  2023/10/29 20:00
     */
    Page<HrCheckAttenanceRule> page(HrCheckAttenanceRulePageParam hrCheckAttenanceRulePageParam);

    /**
     * 添加考勤-考勤以及申请类的规则表数据
     *
     * @author ljy
     * @date  2023/10/29 20:00
     */
    void add(HrCheckAttenanceRuleAddParam hrCheckAttenanceRuleAddParam);

    /**
     * 编辑考勤-考勤以及申请类的规则表数据
     *
     * @author ljy
     * @date  2023/10/29 20:00
     */
    void edit(HrCheckAttenanceRuleEditParam hrCheckAttenanceRuleEditParam);

    /**
     * 删除考勤-考勤以及申请类的规则表数据
     *
     * @author ljy
     * @date  2023/10/29 20:00
     */
    void delete(List<HrCheckAttenanceRuleIdParam> hrCheckAttenanceRuleIdParamList);

    /**
     * 获取考勤-考勤以及申请类的规则表数据详情
     *
     * @author ljy
     * @date  2023/10/29 20:00
     */
    HrCheckAttenanceRule detail(HrCheckAttenanceRuleIdParam hrCheckAttenanceRuleIdParam);

    /**
     * 获取考勤-考勤以及申请类的规则表数据详情
     *
     * @author ljy
     * @date  2023/10/29 20:00
     **/
    HrCheckAttenanceRule queryEntity(String id);
}
