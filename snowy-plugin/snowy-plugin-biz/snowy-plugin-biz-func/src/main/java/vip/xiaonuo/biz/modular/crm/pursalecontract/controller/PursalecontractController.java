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
package vip.xiaonuo.biz.modular.crm.pursalecontract.controller;

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
import vip.xiaonuo.biz.modular.crm.pursalecontract.entity.Pursalecontract;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractAddParam;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractEditParam;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractIdParam;
import vip.xiaonuo.biz.modular.crm.pursalecontract.param.PursalecontractPageParam;
import vip.xiaonuo.biz.modular.crm.pursalecontract.service.PursalecontractService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-CRM-购销合同控制器
 *
 * @author yong.ye
 * @date  2023/06/30 16:03
 */
@Api(tags = "业务表-CRM-购销合同控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PursalecontractController {

    @Resource
    private PursalecontractService pursalecontractService;

    /**
     * 获取业务表-CRM-购销合同分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-CRM-购销合同分页   【api/crm/contract/GetPurSaleContractPage】")
    @GetMapping("/biz/crm/pursalecontract/page")
    public CommonResult<Page<Pursalecontract>> page(PursalecontractPageParam pursalecontractPageParam) {
        return CommonResult.data(pursalecontractService.page(pursalecontractPageParam));
    }

    /**
     * 添加业务表-CRM-购销合同 【审批流】
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-CRM-购销合同  【审批流】")
    @CommonLog("添加业务表-CRM-购销合同")
    @PostMapping("/biz/crm/pursalecontract/add")
    public CommonResult<String> add(@RequestBody @Valid PursalecontractAddParam pursalecontractAddParam) {
        pursalecontractService.addStart(pursalecontractAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-CRM-购销合同 【审批流】
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-CRM-购销合同 【审批流】")
    @CommonLog("编辑业务表-CRM-购销合同")
    @PostMapping("/biz/crm/pursalecontract/edit")
    public CommonResult<String> edit(@RequestBody @Valid PursalecontractEditParam pursalecontractEditParam) {
        pursalecontractService.editStart(pursalecontractEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-CRM-购销合同
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-CRM-购销合同  【api/crm/contract/DeletePurSaleContract】")
    @CommonLog("删除业务表-CRM-购销合同")
    @PostMapping("/biz/crm/pursalecontract/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PursalecontractIdParam> pursalecontractIdParamList) {
        pursalecontractService.delete(pursalecontractIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-CRM-购销合同详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-CRM-购销合同详情  【api/crm/contract/GetPurSaleContractById】")
    @GetMapping("/biz/crm/pursalecontract/detail")
    public CommonResult<Pursalecontract> detail(@Valid PursalecontractIdParam pursalecontractIdParam) {
        return CommonResult.data(pursalecontractService.detail(pursalecontractIdParam));
    }

    /**
     * 获取业务表-CRM-购销合同分页【含权限设置-由于page接口有许多调用，故但起一个权限接口】
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取业务表-CRM-分包合同分页 【含权限设置-由于page接口有许多调用，故但起一个权限接口】")
    @SaCheckPermission("/biz/crm/pursalecontract/limitPage")
    @GetMapping("/biz/crm/pursalecontract/limitPage")
    public CommonResult<Page<Pursalecontract>> limitPage(PursalecontractPageParam pursalecontractPageParam) {
        return CommonResult.data(pursalecontractService.limitPage(pursalecontractPageParam));
    }

    /**
     * 购销合同（流程发起+新增业务数据）
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("购销合同（流程发起+新增业务数据） ")
    @CommonLog("购销合同（流程发起+新增业务数据）")
    @PostMapping("/biz/crm/pursalecontract/addStart")
    public CommonResult<String> addStart(@RequestBody @Valid PursalecontractAddParam pursalecontractAddParam) {
        pursalecontractService.addStart(pursalecontractAddParam);
        return CommonResult.ok();
    }

    /**
     * 购销合同（流程发起+编辑业务数据）
     *
     * @author yong.ye
     * @date  2023/06/30 16:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("购销合同（流程发起+编辑业务数据）")
    @CommonLog("购销合同（流程发起+编辑业务数据）")
    @PostMapping("/biz/crm/pursalecontract/editStart")
    public CommonResult<String> editStart(@RequestBody @Valid PursalecontractEditParam pursalecontractEditParam) {
        pursalecontractService.editStart(pursalecontractEditParam);
        return CommonResult.ok();
    }
}
