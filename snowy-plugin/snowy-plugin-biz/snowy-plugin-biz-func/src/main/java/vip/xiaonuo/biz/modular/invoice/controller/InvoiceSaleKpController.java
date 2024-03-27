///*
// * Copyright [2022] [https://www.xiaonuo.vip]
// *
// * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
// *
// * 1.请不要删除和修改根目录下的LICENSE文件。
// * 2.请不要删除和修改Snowy源码头部的版权声明。
// * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
// * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
// * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
// * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
// */
//package vip.xiaonuo.biz.modular.invoice.controller;
//
//import cn.dev33.satoken.annotation.SaCheckPermission;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
//import com.github.xiaoymin.knife4j.annotations.ApiSupport;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import vip.xiaonuo.common.annotation.CommonLog;
//import vip.xiaonuo.common.pojo.CommonResult;
//import vip.xiaonuo.common.pojo.CommonValidList;
//import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleKp;
//import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleKpAddParam;
//import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleKpEditParam;
//import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleKpIdParam;
//import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleKpPageParam;
//import vip.xiaonuo.biz.modular.invoice.service.InvoiceSaleKpService;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
///**
// * 财务系统-销售发票开具申请-开票信息控制器
// *
// * @author yong.ye
// * @date  2024/03/19 10:18
// */
//@Api(tags = "财务系统-销售发票开具申请-开票信息控制器")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class InvoiceSaleKpController {
//
//    @Resource
//    private InvoiceSaleKpService invoiceSaleKpService;
//
//    /**
//     * 获取财务系统-销售发票开具申请-开票信息分页
//     *
//     * @author yong.ye
//     * @date  2024/03/19 10:18
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取财务系统-销售发票开具申请-开票信息分页")
////    @SaCheckPermission("/biz/invoice/sale/kp/page")
//    @GetMapping("/biz/invoice/sale/kp/page")
//    public CommonResult<Page<InvoiceSaleKp>> page(InvoiceSaleKpPageParam invoiceSaleKpPageParam) {
//        return CommonResult.data(invoiceSaleKpService.page(invoiceSaleKpPageParam));
//    }
//
//    /**
//     * 添加财务系统-销售发票开具申请-开票信息
//     *
//     * @author yong.ye
//     * @date  2024/03/19 10:18
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加财务系统-销售发票开具申请-开票信息")
//    @CommonLog("添加财务系统-销售发票开具申请-开票信息")
////    @SaCheckPermission("/biz/invoice/sale/kp/add")
//    @PostMapping("/biz/invoice/sale/kp/add")
//    public CommonResult<String> add(@RequestBody @Valid InvoiceSaleKpAddParam invoiceSaleKpAddParam) {
//        invoiceSaleKpService.add(invoiceSaleKpAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑财务系统-销售发票开具申请-开票信息
//     *
//     * @author yong.ye
//     * @date  2024/03/19 10:18
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑财务系统-销售发票开具申请-开票信息")
//    @CommonLog("编辑财务系统-销售发票开具申请-开票信息")
////    @SaCheckPermission("/biz/invoice/sale/kp/edit")
//    @PostMapping("/biz/invoice/sale/kp/edit")
//    public CommonResult<String> edit(@RequestBody @Valid InvoiceSaleKpEditParam invoiceSaleKpEditParam) {
//        invoiceSaleKpService.edit(invoiceSaleKpEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除财务系统-销售发票开具申请-开票信息
//     *
//     * @author yong.ye
//     * @date  2024/03/19 10:18
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除财务系统-销售发票开具申请-开票信息")
//    @CommonLog("删除财务系统-销售发票开具申请-开票信息")
////    @SaCheckPermission("/biz/invoice/sale/kp/delete")
//    @PostMapping("/biz/invoice/sale/kp/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<InvoiceSaleKpIdParam> invoiceSaleKpIdParamList) {
//        invoiceSaleKpService.delete(invoiceSaleKpIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取财务系统-销售发票开具申请-开票信息详情
//     *
//     * @author yong.ye
//     * @date  2024/03/19 10:18
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取财务系统-销售发票开具申请-开票信息详情")
////    @SaCheckPermission("/biz/invoice/sale/kp/detail")
//    @GetMapping("/biz/invoice/sale/kp/detail")
//    public CommonResult<InvoiceSaleKp> detail(@Valid InvoiceSaleKpIdParam invoiceSaleKpIdParam) {
//        return CommonResult.data(invoiceSaleKpService.detail(invoiceSaleKpIdParam));
//    }
//}
