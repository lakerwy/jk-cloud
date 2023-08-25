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
package vip.xiaonuo.biz.modular.recruit.controller;

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
import vip.xiaonuo.biz.modular.recruit.param.dto.RecruitPlanFlwDto;
import vip.xiaonuo.biz.modular.recruit.param.dto.RecruitPlanPageDto;
import vip.xiaonuo.biz.modular.recruit.vo.BizRecruitPlanVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitPlan;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitPlanEditParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitPlanIdParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitPlanPageParam;
import vip.xiaonuo.biz.modular.recruit.service.BizRecruitPlanService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 业务表-招聘-部门年度计划表控制器
 *
 * @author yong.ye
 * @date  2023/08/23 09:02
 */
@Api(tags = "业务表-招聘-部门年度计划表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizRecruitPlanController {

    @Resource
    private BizRecruitPlanService bizRecruitPlanService;

    /**
     * 获取业务表-招聘-部门年度计划表
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-招聘-部门年度计划表")
    @GetMapping("/biz/recruit/plan/page")
    public CommonResult<List<BizRecruitPlanVo>> page(RecruitPlanPageDto recruitPlanPageDto) {
        return CommonResult.data(bizRecruitPlanService.page(recruitPlanPageDto));
    }

    /**
     * 添加业务表-招聘-部门年度计划表
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-招聘-部门年度计划表")
    @CommonLog("添加业务表-招聘-部门年度计划表")
    @PostMapping("/biz/recruit/plan/add")
    public CommonResult<String> add(@RequestBody @Valid RecruitPlanFlwDto recruitPlanFlwDto) {
        bizRecruitPlanService.add(recruitPlanFlwDto);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-招聘-部门年度计划表
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-招聘-部门年度计划表")
    @CommonLog("编辑业务表-招聘-部门年度计划表")
    @PostMapping("/biz/recruit/plan/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizRecruitPlanEditParam bizRecruitPlanEditParam) {
        bizRecruitPlanService.edit(bizRecruitPlanEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-招聘-部门年度计划表
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-招聘-部门年度计划表")
    @CommonLog("删除业务表-招聘-部门年度计划表")
    @PostMapping("/biz/recruit/plan/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizRecruitPlanIdParam> bizRecruitPlanIdParamList) {
        bizRecruitPlanService.delete(bizRecruitPlanIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-招聘-部门年度计划表详情
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-招聘-部门年度计划表详情")
    @GetMapping("/biz/recruit/plan/detail")
    public CommonResult<BizRecruitPlan> detail(@Valid BizRecruitPlanIdParam bizRecruitPlanIdParam) {
        return CommonResult.data(bizRecruitPlanService.detail(bizRecruitPlanIdParam));
    }

    /**
     * 添加业务表-招聘-部门年度计划表(流程中新增)
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-招聘-部门年度计划表(流程中新增)")
    @CommonLog("添加业务表-招聘-部门年度计划表(流程中新增)")
    @PostMapping("/biz/recruit/plan/flwAdd")
    public CommonResult<String> flwAdd(@RequestBody @Valid RecruitPlanFlwDto recruitPlanFlwDto) {
        bizRecruitPlanService.flwAdd(recruitPlanFlwDto);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-招聘-部门年度计划表(流程中编辑)
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-招聘-部门年度计划表(流程中编辑)")
    @CommonLog("编辑业务表-招聘-部门年度计划表(流程中编辑)")
    @PostMapping("/biz/recruit/plan/flwEdit")
    public CommonResult<String> flwEdit(@RequestBody @Valid RecruitPlanFlwDto recruitPlanFlwDto) {
        bizRecruitPlanService.flwAdd(recruitPlanFlwDto);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-招聘-部门年度计划集合（页面选择项）
     *
     * @author yong.ye
     * @date  2023/08/23 09:02
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-招聘-部门年度计划集合（页面选择项）")
    @GetMapping("/biz/recruit/plan/planSelect")
    public CommonResult<Page<BizRecruitPlan>> planSelect(BizRecruitPlanPageParam bizRecruitPlanPageParam) {
        return CommonResult.data(bizRecruitPlanService.planSelect(bizRecruitPlanPageParam));
    }
}
