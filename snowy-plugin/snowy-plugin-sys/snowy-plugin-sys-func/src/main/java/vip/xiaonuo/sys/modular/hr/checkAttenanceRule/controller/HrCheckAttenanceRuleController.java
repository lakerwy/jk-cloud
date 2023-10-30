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
package vip.xiaonuo.sys.modular.hr.checkAttenanceRule.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.sys.modular.hr.checkAttenanceRule.entity.HrCheckAttenanceRule;
import vip.xiaonuo.sys.modular.hr.checkAttenanceRule.param.HrCheckAttenanceRuleAddParam;
import vip.xiaonuo.sys.modular.hr.checkAttenanceRule.param.HrCheckAttenanceRuleEditParam;
import vip.xiaonuo.sys.modular.hr.checkAttenanceRule.param.HrCheckAttenanceRuleIdParam;
import vip.xiaonuo.sys.modular.hr.checkAttenanceRule.param.HrCheckAttenanceRulePageParam;
import vip.xiaonuo.sys.modular.hr.checkAttenanceRule.service.HrCheckAttenanceRuleService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 考勤-考勤以及申请类的规则表数据控制器
 *
 * @author ljy
 * @date  2023/10/29 20:00
 */
@Api(tags = "考勤-考勤以及申请类的规则表数据控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrCheckAttenanceRuleController {

    @Resource
    private HrCheckAttenanceRuleService hrCheckAttenanceRuleService;

    /**
     * 获取考勤-考勤以及申请类的规则表数据分页
     *
     * @author ljy
     * @date  2023/10/29 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取考勤-考勤以及申请类的规则表数据分页")
    @SaCheckPermission("/sys/checkAttenanceRule/page")
    @GetMapping("/sys/checkAttenanceRule/page")
    public CommonResult<Page<HrCheckAttenanceRule>> page(HrCheckAttenanceRulePageParam hrCheckAttenanceRulePageParam) {
        return CommonResult.data(hrCheckAttenanceRuleService.page(hrCheckAttenanceRulePageParam));
    }

    /**
     * 添加考勤-考勤以及申请类的规则表数据
     *
     * @author ljy
     * @date  2023/10/29 20:00
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加考勤-考勤以及申请类的规则表数据")
    @CommonLog("添加考勤-考勤以及申请类的规则表数据")
    @SaCheckPermission("/sys/checkAttenanceRule/add")
    @PostMapping("/sys/checkAttenanceRule/add")
    public CommonResult<String> add(@RequestBody @Valid HrCheckAttenanceRuleAddParam hrCheckAttenanceRuleAddParam) {
        hrCheckAttenanceRuleService.add(hrCheckAttenanceRuleAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤-考勤以及申请类的规则表数据
     *
     * @author ljy
     * @date  2023/10/29 20:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑考勤-考勤以及申请类的规则表数据")
    @CommonLog("编辑考勤-考勤以及申请类的规则表数据")
    @SaCheckPermission("/sys/checkAttenanceRule/edit")
    @PostMapping("/sys/checkAttenanceRule/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrCheckAttenanceRuleEditParam hrCheckAttenanceRuleEditParam) {
        hrCheckAttenanceRuleService.edit(hrCheckAttenanceRuleEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除考勤-考勤以及申请类的规则表数据
     *
     * @author ljy
     * @date  2023/10/29 20:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除考勤-考勤以及申请类的规则表数据")
    @CommonLog("删除考勤-考勤以及申请类的规则表数据")
    @SaCheckPermission("/sys/checkAttenanceRule/delete")
    @PostMapping("/sys/checkAttenanceRule/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrCheckAttenanceRuleIdParam> hrCheckAttenanceRuleIdParamList) {
        hrCheckAttenanceRuleService.delete(hrCheckAttenanceRuleIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取考勤-考勤以及申请类的规则表数据详情
     *
     * @author ljy
     * @date  2023/10/29 20:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取考勤-考勤以及申请类的规则表数据详情")
    @SaCheckPermission("/sys/checkAttenanceRule/detail")
    @GetMapping("/sys/checkAttenanceRule/detail")
    public CommonResult<HrCheckAttenanceRule> detail(@Valid HrCheckAttenanceRuleIdParam hrCheckAttenanceRuleIdParam) {
        return CommonResult.data(hrCheckAttenanceRuleService.detail(hrCheckAttenanceRuleIdParam));
    }
}
