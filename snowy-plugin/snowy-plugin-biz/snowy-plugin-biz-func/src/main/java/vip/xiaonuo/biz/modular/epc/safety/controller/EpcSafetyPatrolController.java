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
package vip.xiaonuo.biz.modular.epc.safety.controller;

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
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcSafetyPatrol;
import vip.xiaonuo.biz.modular.epc.safety.param.*;
import vip.xiaonuo.biz.modular.epc.safety.service.EpcSafetyPatrolService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * EPC-安全巡查控制器
 *
 * @author yong.ye
 * @date  2023/09/05 14:59
 */
@Api(tags = "EPC-安全巡查控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EpcSafetyPatrolController {

    @Resource
    private EpcSafetyPatrolService epcSafetyPatrolService;

    /**
     * 获取EPC-安全巡查分页
     *
     * @author yong.ye
     * @date  2023/09/05 14:59
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取EPC-安全巡查分页")
//    @SaCheckPermission("/biz/epc/patrol/page")
    @GetMapping("/biz/epc/patrol/page")
    public CommonResult<Page<EpcSafetyPatrol>> page(EpcSafetyPatrolPageParam epcSafetyPatrolPageParam) {
        return CommonResult.data(epcSafetyPatrolService.page(epcSafetyPatrolPageParam));
    }

    /**
     * 添加EPC-安全巡查
     *
     * @author yong.ye
     * @date  2023/09/05 14:59
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加EPC-安全巡查")
    @CommonLog("添加EPC-安全巡查")
//    @SaCheckPermission("/biz/epc/patrol/add")
    @PostMapping("/biz/epc/patrol/add")
    public CommonResult<String> add(@RequestBody @Valid EpcSafetyPatrolAddParam epcSafetyPatrolAddParam) {
        epcSafetyPatrolService.add(epcSafetyPatrolAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑EPC-安全巡查
     *
     * @author yong.ye
     * @date  2023/09/05 14:59
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑EPC-安全巡查")
    @CommonLog("编辑EPC-安全巡查")
//    @SaCheckPermission("/biz/epc/patrol/edit")
    @PostMapping("/biz/epc/patrol/edit")
    public CommonResult<String> edit(@RequestBody @Valid EpcSafetyPatrolEditParam epcSafetyPatrolEditParam) {
        epcSafetyPatrolService.edit(epcSafetyPatrolEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除EPC-安全巡查
     *
     * @author yong.ye
     * @date  2023/09/05 14:59
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除EPC-安全巡查")
    @CommonLog("删除EPC-安全巡查")
//    @SaCheckPermission("/biz/epc/patrol/delete")
    @PostMapping("/biz/epc/patrol/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EpcSafetyPatrolIdParam> epcSafetyPatrolIdParamList) {
        epcSafetyPatrolService.delete(epcSafetyPatrolIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取EPC-安全巡查详情
     *
     * @author yong.ye
     * @date  2023/09/05 14:59
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取EPC-安全巡查详情")
//    @SaCheckPermission("/biz/epc/patrol/detail")
    @GetMapping("/biz/epc/patrol/detail")
    public CommonResult<EpcSafetyPatrol> detail(@Valid EpcSafetyPatrolIdParam epcSafetyPatrolIdParam) {
        return CommonResult.data(epcSafetyPatrolService.detail(epcSafetyPatrolIdParam));
    }
}
