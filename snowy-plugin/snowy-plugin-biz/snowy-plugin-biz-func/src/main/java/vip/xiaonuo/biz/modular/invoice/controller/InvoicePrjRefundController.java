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
package vip.xiaonuo.biz.modular.invoice.controller;

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
import vip.xiaonuo.biz.modular.invoice.param.Customer.DoImport;
import vip.xiaonuo.biz.modular.invoice.param.Customer.ImportRefund;
import vip.xiaonuo.biz.modular.invoice.vo.ImportResult;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.invoice.entity.InvoicePrjRefund;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundPageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoicePrjRefundService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务系统-项目回款录入申请控制器
 *
 * @author yong.ye
 * @date  2024/03/19 10:24
 */
@Api(tags = "财务系统-项目回款录入申请控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class InvoicePrjRefundController {

    @Resource
    private InvoicePrjRefundService invoicePrjRefundService;

    /**
     * 获取财务系统-项目回款录入申请分页
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务系统-项目回款录入申请分页")
//    @SaCheckPermission("/biz/invoice/prj/refund/page")
    @GetMapping("/biz/invoice/prj/refund/page")
    public CommonResult<Page<InvoicePrjRefund>> page(InvoicePrjRefundPageParam invoicePrjRefundPageParam) {
        return CommonResult.data(invoicePrjRefundService.page(invoicePrjRefundPageParam));
    }

    /**
     * 添加财务系统-项目回款录入申请
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务系统-项目回款录入申请")
    @CommonLog("添加财务系统-项目回款录入申请")
//    @SaCheckPermission("/biz/invoice/prj/refund/add")
    @PostMapping("/biz/invoice/prj/refund/add")
    public CommonResult<String> add(@RequestBody @Valid InvoicePrjRefundAddParam invoicePrjRefundAddParam) {
        invoicePrjRefundService.add(invoicePrjRefundAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务系统-项目回款录入申请
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务系统-项目回款录入申请")
    @CommonLog("编辑财务系统-项目回款录入申请")
//    @SaCheckPermission("/biz/invoice/prj/refund/edit")
    @PostMapping("/biz/invoice/prj/refund/edit")
    public CommonResult<String> edit(@RequestBody @Valid InvoicePrjRefundEditParam invoicePrjRefundEditParam) {
        invoicePrjRefundService.edit(invoicePrjRefundEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务系统-项目回款录入申请
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务系统-项目回款录入申请")
    @CommonLog("删除财务系统-项目回款录入申请")
//    @SaCheckPermission("/biz/invoice/prj/refund/delete")
    @PostMapping("/biz/invoice/prj/refund/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<InvoicePrjRefundIdParam> invoicePrjRefundIdParamList) {
        invoicePrjRefundService.delete(invoicePrjRefundIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务系统-项目回款录入申请详情
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务系统-项目回款录入申请详情")
//    @SaCheckPermission("/biz/invoice/prj/refund/detail")
    @GetMapping("/biz/invoice/prj/refund/detail")
    public CommonResult<InvoicePrjRefund> detail(@Valid InvoicePrjRefundIdParam invoicePrjRefundIdParam) {
        return CommonResult.data(invoicePrjRefundService.detail(invoicePrjRefundIdParam));
    }

    /**
     * 财务系统-项目回款录入申请 - 导入校验
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("财务系统-项目回款录入申请 - 导入校验")
    @CommonLog("财务系统-项目回款录入申请 - 导入校验")
    @PostMapping("/biz/invoice/prj/refund/importRefund")
    public CommonResult<ImportResult> importRefund(@RequestBody @Valid ImportRefund importRefund) {
        return CommonResult.data(invoicePrjRefundService.importRefund(importRefund));
    }

    /**
     * 财务系统-项目回款录入申请 - 【正式导入】
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("财务系统-项目回款录入申请 - 【正式导入】")
    @CommonLog("财务系统-项目回款录入申请 - 【正式导入】")
    @PostMapping("/biz/invoice/prj/refund/doImport")
    public CommonResult<Boolean> doImport(@RequestBody @Valid DoImport input) {
        return CommonResult.data(invoicePrjRefundService.doImport(input));
    }
}
