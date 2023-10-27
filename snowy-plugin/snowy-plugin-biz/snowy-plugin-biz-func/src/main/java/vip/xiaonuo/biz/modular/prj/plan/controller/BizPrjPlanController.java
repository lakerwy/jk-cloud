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
package vip.xiaonuo.biz.modular.prj.plan.controller;

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
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlan;
import vip.xiaonuo.biz.modular.prj.plan.param.*;
import vip.xiaonuo.biz.modular.prj.plan.service.BizPrjPlanService;
import vip.xiaonuo.biz.modular.prj.plan.vo.BizPrjPlanVo;
import vip.xiaonuo.biz.modular.prj.plan.vo.PlanInvoiceVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 业务表-项目计划付款单控制器
 *
 * @author yong.ye
 * @date  2023/09/28 11:25
 */
@Api(tags = "业务表-项目计划付款单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizPrjPlanController {

    @Resource
    private BizPrjPlanService bizPrjPlanService;

    /**
     * 获取业务表-项目计划付款单分页
     *
     * @author yong.ye
     * @date  2023/09/28 11:25
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-项目计划付款单分页")
//    @SaCheckPermission("/biz/prj/plan/page")
    @GetMapping("/biz/prj/plan/page")
    public CommonResult<Page<BizPrjPlanVo>> page(BizPrjPlanPageParam bizPrjPlanPageParam) {
        return CommonResult.data(bizPrjPlanService.page(bizPrjPlanPageParam));
    }

    /**
     * 添加业务表-项目计划付款单
     *
     * @author yong.ye
     * @date  2023/09/28 11:25
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-项目计划付款单")
    @CommonLog("添加业务表-项目计划付款单")
//    @SaCheckPermission("/biz/prj/plan/add")
    @PostMapping("/biz/prj/plan/add")
    public CommonResult<String> add(@RequestBody @Valid PrjPlanParam prjPlanParam) {
        bizPrjPlanService.add(prjPlanParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-项目计划付款单
     *
     * @author yong.ye
     * @date  2023/09/28 11:25
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-项目计划付款单")
    @CommonLog("编辑业务表-项目计划付款单")
//    @SaCheckPermission("/biz/prj/plan/edit")
    @PostMapping("/biz/prj/plan/edit")
    public CommonResult<String> edit(@RequestBody @Valid PrjPlanParam prjPlanParam) {
        bizPrjPlanService.edit(prjPlanParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-项目计划付款单
     *
     * @author yong.ye
     * @date  2023/09/28 11:25
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-项目计划付款单")
    @CommonLog("删除业务表-项目计划付款单")
//    @SaCheckPermission("/biz/prj/plan/delete")
    @PostMapping("/biz/prj/plan/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizPrjPlanIdParam> bizPrjPlanIdParamList) {
        bizPrjPlanService.delete(bizPrjPlanIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-项目计划付款单详情
     *
     * @author yong.ye
     * @date  2023/09/28 11:25
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-项目计划付款单详情")
//    @SaCheckPermission("/biz/prj/plan/detail")
    @GetMapping("/biz/prj/plan/detail")
    public CommonResult<BizPrjPlan> detail(@Valid BizPrjPlanIdParam bizPrjPlanIdParam) {
        return CommonResult.data(bizPrjPlanService.detail(bizPrjPlanIdParam));
    }

    /**
     * 根据 【合同编号】 - 获取 审核通过 的【项目计划付款单】历史发票集合
     *
     * @author yong.ye
     * @date  2023/10/19 11:25
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("根据 【合同编号】 - 获取 审核通过 的【项目计划付款单】历史发票集合")
//    @SaCheckPermission("/biz/prj/plan/invoiceList")
    @GetMapping("/biz/prj/plan/invoiceList")
    public CommonResult<List<PlanInvoiceVo>> invoiceList(@Valid InvoiceParam invoiceParam) {
        return CommonResult.data(bizPrjPlanService.invoiceList(invoiceParam));
    }


    /**
     * 根据 【合同编号】 - 获取 审核通过 的历史【项目款计划单】集合
     *
     * @author yong.ye
     * @date  2023/10/19 16:36
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("根据 【合同编号】 - 获取 审核通过 的历史【项目款计划单】集合")
    @GetMapping("/biz/prj/plan/planList")
    public CommonResult<List<BizPrjPlanVo>> planList(@Valid InvoiceParam invoiceParam) {
        return CommonResult.data(bizPrjPlanService.planList(invoiceParam));
    }
}
