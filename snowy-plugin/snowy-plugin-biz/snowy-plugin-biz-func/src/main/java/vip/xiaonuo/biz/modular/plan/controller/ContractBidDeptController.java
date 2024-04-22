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
//package vip.xiaonuo.biz.modular.plan.controller;
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
//import vip.xiaonuo.biz.modular.plan.entity.ContractBidDept;
//import vip.xiaonuo.biz.modular.plan.param.ContractBidDeptAddParam;
//import vip.xiaonuo.biz.modular.plan.param.ContractBidDeptEditParam;
//import vip.xiaonuo.biz.modular.plan.param.ContractBidDeptIdParam;
//import vip.xiaonuo.biz.modular.plan.param.ContractBidDeptPageParam;
//import vip.xiaonuo.biz.modular.plan.service.ContractBidDeptService;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
///**
// * 合约规划 - 投标组织控制器
// *
// * @author yong.ye
// * @date  2024/04/22 11:42
// */
//@Api(tags = "合约规划 - 投标组织控制器")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class ContractBidDeptController {
//
//    @Resource
//    private ContractBidDeptService contractBidDeptService;
//
//    /**
//     * 获取合约规划 - 投标组织分页
//     *
//     * @author yong.ye
//     * @date  2024/04/22 11:42
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取合约规划 - 投标组织分页")
////    @SaCheckPermission("/biz/plan/dept/page")
//    @GetMapping("/biz/plan/dept/page")
//    public CommonResult<Page<ContractBidDept>> page(ContractBidDeptPageParam contractBidDeptPageParam) {
//        return CommonResult.data(contractBidDeptService.page(contractBidDeptPageParam));
//    }
//
//    /**
//     * 添加合约规划 - 投标组织
//     *
//     * @author yong.ye
//     * @date  2024/04/22 11:42
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加合约规划 - 投标组织")
//    @CommonLog("添加合约规划 - 投标组织")
////    @SaCheckPermission("/biz/plan/dept/add")
//    @PostMapping("/biz/plan/dept/add")
//    public CommonResult<String> add(@RequestBody @Valid ContractBidDeptAddParam contractBidDeptAddParam) {
//        contractBidDeptService.add(contractBidDeptAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑合约规划 - 投标组织
//     *
//     * @author yong.ye
//     * @date  2024/04/22 11:42
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑合约规划 - 投标组织")
//    @CommonLog("编辑合约规划 - 投标组织")
////    @SaCheckPermission("/biz/plan/dept/edit")
//    @PostMapping("/biz/plan/dept/edit")
//    public CommonResult<String> edit(@RequestBody @Valid ContractBidDeptEditParam contractBidDeptEditParam) {
//        contractBidDeptService.edit(contractBidDeptEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除合约规划 - 投标组织
//     *
//     * @author yong.ye
//     * @date  2024/04/22 11:42
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除合约规划 - 投标组织")
//    @CommonLog("删除合约规划 - 投标组织")
////    @SaCheckPermission("/biz/plan/dept/delete")
//    @PostMapping("/biz/plan/dept/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<ContractBidDeptIdParam> contractBidDeptIdParamList) {
//        contractBidDeptService.delete(contractBidDeptIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取合约规划 - 投标组织详情
//     *
//     * @author yong.ye
//     * @date  2024/04/22 11:42
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取合约规划 - 投标组织详情")
////    @SaCheckPermission("/biz/plan/dept/detail")
//    @GetMapping("/biz/plan/dept/detail")
//    public CommonResult<ContractBidDept> detail(@Valid ContractBidDeptIdParam contractBidDeptIdParam) {
//        return CommonResult.data(contractBidDeptService.detail(contractBidDeptIdParam));
//    }
//}
