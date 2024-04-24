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
package vip.xiaonuo.biz.modular.plan.controller;

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
import vip.xiaonuo.biz.modular.plan.vo.ContractPlanVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.plan.entity.ContractPlan;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanAddParam;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanEditParam;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanIdParam;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanPageParam;
import vip.xiaonuo.biz.modular.plan.service.ContractPlanService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 合约规划 - 申请流程控制器
 *
 * @author yong.ye
 * @date  2024/04/22 11:41
 */
@Api(tags = "合约规划 - 申请流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ContractPlanController {

    @Resource
    private ContractPlanService contractPlanService;

    /**
     * 获取合约规划 - 申请流程分页
     *
     * @author yong.ye
     * @date  2024/04/22 11:41
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取合约规划 - 申请流程分页")
//    @SaCheckPermission("/biz/plan/page")
    @GetMapping("/biz/plan/page")
    public CommonResult<Page<ContractPlanVo>> page(ContractPlanPageParam contractPlanPageParam) {
        return CommonResult.data(contractPlanService.page(contractPlanPageParam));
    }

    /**
     * 添加合约规划 - 申请流程
     *
     * @author yong.ye
     * @date  2024/04/22 11:41
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加合约规划 - 申请流程")
    @CommonLog("添加合约规划 - 申请流程")
//    @SaCheckPermission("/biz/plan/add")
    @PostMapping("/biz/plan/add")
    public CommonResult<String> add(@RequestBody @Valid ContractPlanAddParam contractPlanAddParam) {
        contractPlanService.add(contractPlanAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑合约规划 - 申请流程
     *
     * @author yong.ye
     * @date  2024/04/22 11:41
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑合约规划 - 申请流程")
    @CommonLog("编辑合约规划 - 申请流程")
//    @SaCheckPermission("/biz/plan/edit")
    @PostMapping("/biz/plan/edit")
    public CommonResult<String> edit(@RequestBody @Valid ContractPlanEditParam contractPlanEditParam) {
        contractPlanService.edit(contractPlanEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除合约规划 - 申请流程
     *
     * @author yong.ye
     * @date  2024/04/22 11:41
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除合约规划 - 申请流程")
    @CommonLog("删除合约规划 - 申请流程")
//    @SaCheckPermission("/biz/plan/delete")
    @PostMapping("/biz/plan/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ContractPlanIdParam> contractPlanIdParamList) {
        contractPlanService.delete(contractPlanIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取合约规划 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/04/22 11:41
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取合约规划 - 申请流程详情")
//    @SaCheckPermission("/biz/plan/detail")
    @GetMapping("/biz/plan/detail")
    public CommonResult<ContractPlanVo> detail(@Valid ContractPlanIdParam contractPlanIdParam) {
        return CommonResult.data(contractPlanService.detail(contractPlanIdParam));
    }
}
