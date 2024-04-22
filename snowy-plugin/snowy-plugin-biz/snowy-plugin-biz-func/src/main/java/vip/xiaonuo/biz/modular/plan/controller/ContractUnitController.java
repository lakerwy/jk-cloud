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
//import vip.xiaonuo.biz.modular.plan.entity.ContractUnit;
//import vip.xiaonuo.biz.modular.plan.param.ContractUnitAddParam;
//import vip.xiaonuo.biz.modular.plan.param.ContractUnitEditParam;
//import vip.xiaonuo.biz.modular.plan.param.ContractUnitIdParam;
//import vip.xiaonuo.biz.modular.plan.param.ContractUnitPageParam;
//import vip.xiaonuo.biz.modular.plan.service.ContractUnitService;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//
///**
// * 合约规划 - 申报（项目承包方）-收入&效益控制器
// *
// * @author yong.ye
// * @date  2024/04/22 11:43
// */
//@Api(tags = "合约规划 - 申报（项目承包方）-收入&效益控制器")
//@ApiSupport(author = "SNOWY_TEAM", order = 1)
//@RestController
//@Validated
//public class ContractUnitController {
//
//    @Resource
//    private ContractUnitService contractUnitService;
//
//    /**
//     * 获取合约规划 - 申报（项目承包方）-收入&效益分页
//     *
//     * @author yong.ye
//     * @date  2024/04/22 11:43
//     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取合约规划 - 申报（项目承包方）-收入&效益分页")
////    @SaCheckPermission("/biz/plan/unit/page")
//    @GetMapping("/biz/plan/unit/page")
//    public CommonResult<Page<ContractUnit>> page(ContractUnitPageParam contractUnitPageParam) {
//        return CommonResult.data(contractUnitService.page(contractUnitPageParam));
//    }
//
//    /**
//     * 添加合约规划 - 申报（项目承包方）-收入&效益
//     *
//     * @author yong.ye
//     * @date  2024/04/22 11:43
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加合约规划 - 申报（项目承包方）-收入&效益")
//    @CommonLog("添加合约规划 - 申报（项目承包方）-收入&效益")
////    @SaCheckPermission("/biz/plan/unit/add")
//    @PostMapping("/biz/plan/unit/add")
//    public CommonResult<String> add(@RequestBody @Valid ContractUnitAddParam contractUnitAddParam) {
//        contractUnitService.add(contractUnitAddParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 编辑合约规划 - 申报（项目承包方）-收入&效益
//     *
//     * @author yong.ye
//     * @date  2024/04/22 11:43
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑合约规划 - 申报（项目承包方）-收入&效益")
//    @CommonLog("编辑合约规划 - 申报（项目承包方）-收入&效益")
////    @SaCheckPermission("/biz/plan/unit/edit")
//    @PostMapping("/biz/plan/unit/edit")
//    public CommonResult<String> edit(@RequestBody @Valid ContractUnitEditParam contractUnitEditParam) {
//        contractUnitService.edit(contractUnitEditParam);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 删除合约规划 - 申报（项目承包方）-收入&效益
//     *
//     * @author yong.ye
//     * @date  2024/04/22 11:43
//     */
//    @ApiOperationSupport(order = 4)
//    @ApiOperation("删除合约规划 - 申报（项目承包方）-收入&效益")
//    @CommonLog("删除合约规划 - 申报（项目承包方）-收入&效益")
////    @SaCheckPermission("/biz/plan/unit/delete")
//    @PostMapping("/biz/plan/unit/delete")
//    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
//                                                   CommonValidList<ContractUnitIdParam> contractUnitIdParamList) {
//        contractUnitService.delete(contractUnitIdParamList);
//        return CommonResult.ok();
//    }
//
//    /**
//     * 获取合约规划 - 申报（项目承包方）-收入&效益详情
//     *
//     * @author yong.ye
//     * @date  2024/04/22 11:43
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取合约规划 - 申报（项目承包方）-收入&效益详情")
////    @SaCheckPermission("/biz/plan/unit/detail")
//    @GetMapping("/biz/plan/unit/detail")
//    public CommonResult<ContractUnit> detail(@Valid ContractUnitIdParam contractUnitIdParam) {
//        return CommonResult.data(contractUnitService.detail(contractUnitIdParam));
//    }
//}
