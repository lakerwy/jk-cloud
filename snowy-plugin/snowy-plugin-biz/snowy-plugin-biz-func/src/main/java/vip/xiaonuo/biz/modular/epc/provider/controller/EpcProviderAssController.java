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
package vip.xiaonuo.biz.modular.epc.provider.controller;

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
import vip.xiaonuo.biz.modular.epc.provider.entity.EpcProviderAss;
import vip.xiaonuo.biz.modular.epc.provider.param.*;
import vip.xiaonuo.biz.modular.epc.provider.service.EpcProviderAssService;
import vip.xiaonuo.biz.modular.epc.provider.vo.ProviderAssVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * EPC 供应商库-考核表控制器
 *
 * @author yong.ye
 * @date  2023/09/25 16:26
 */
@Api(tags = "EPC 供应商库-考核表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EpcProviderAssController {

    @Resource
    private EpcProviderAssService epcProviderAssService;

    /**
     * 获取EPC 供应商库-考核表分页
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取EPC 供应商库-考核表分页")
//    @SaCheckPermission("/biz/epc/provider/ass/page")
    @GetMapping("/biz/epc/provider/ass/page")
    public CommonResult<Page<EpcProviderAss>> page(EpcProviderAssPageParam epcProviderAssPageParam) {
        return CommonResult.data(epcProviderAssService.page(epcProviderAssPageParam));
    }

    /**
     * 添加EPC 供应商库-考核表
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加EPC 供应商库-考核表")
    @CommonLog("添加EPC 供应商库-考核表")
//    @SaCheckPermission("/biz/epc/provider/ass/add")
    @PostMapping("/biz/epc/provider/ass/add")
    public CommonResult<String> add(@RequestBody @Valid EpcProviderAssAddParam epcProviderAssAddParam) {
        epcProviderAssService.add(epcProviderAssAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑EPC 供应商库-考核表
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑EPC 供应商库-考核表")
    @CommonLog("编辑EPC 供应商库-考核表")
//    @SaCheckPermission("/biz/epc/provider/ass/edit")
    @PostMapping("/biz/epc/provider/ass/edit")
    public CommonResult<String> edit(@RequestBody @Valid EpcProviderAssEditParam epcProviderAssEditParam) {
        epcProviderAssService.edit(epcProviderAssEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除EPC 供应商库-考核表
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除EPC 供应商库-考核表")
    @CommonLog("删除EPC 供应商库-考核表")
//    @SaCheckPermission("/biz/epc/provider/ass/delete")
    @PostMapping("/biz/epc/provider/ass/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EpcProviderAssIdParam> epcProviderAssIdParamList) {
        epcProviderAssService.delete(epcProviderAssIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取EPC 供应商库-考核表详情
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取EPC 供应商库-考核表详情")
//    @SaCheckPermission("/biz/epc/provider/ass/detail")
    @GetMapping("/biz/epc/provider/ass/detail")
    public CommonResult<EpcProviderAss> detail(@Valid EpcProviderAssIdParam epcProviderAssIdParam) {
        return CommonResult.data(epcProviderAssService.detail(epcProviderAssIdParam));
    }

    /**
     * 校验 同一供应商+同一年+同一季度+同一考核类型 是否已考核
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("校验 同一供应商+同一年+同一季度+同一考核类型 是否已考核")
//    @SaCheckPermission("/biz/epc/provider/ass/check")
    @GetMapping("/biz/epc/provider/ass/check")
    public CommonResult<Boolean> check(@Valid EpcProviderAssCheckParam epcProviderAssCheckParam) {
        return CommonResult.data(epcProviderAssService.check(epcProviderAssCheckParam));
    }


    /**
     * 获取EPC 供应商库-考核 年度 统计表 分页
     *
     * @author yong.ye
     * @date  2023/09/25 16:26
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取EPC 供应商库-考核 年度 统计表 分页")
//    @SaCheckPermission("/biz/epc/provider/ass/pageList")
    @GetMapping("/biz/epc/provider/ass/pageList")
    public CommonResult<Page<ProviderAssVo>> pageList(EpcProviderAssPageParam epcProviderAssPageParam) {
        return CommonResult.data(epcProviderAssService.pageList(epcProviderAssPageParam));
    }
}
