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
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcFacilityDanger;
import vip.xiaonuo.biz.modular.epc.safety.param.*;
import vip.xiaonuo.biz.modular.epc.safety.service.EpcFacilityDangerService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * EPC-危险源识别控制器
 *
 * @author yong.ye
 * @date  2023/09/05 14:51
 */
@Api(tags = "EPC-危险源识别控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EpcFacilityDangerController {

    @Resource
    private EpcFacilityDangerService epcFacilityDangerService;

    /**
     * 获取EPC-危险源识别分页
     *
     * @author yong.ye
     * @date  2023/09/05 14:51
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取EPC-危险源识别分页")
//    @SaCheckPermission("/biz/epc/danger/page")
    @GetMapping("/biz/epc/danger/page")
    public CommonResult<Page<EpcFacilityDanger>> page(EpcFacilityDangerPageParam epcFacilityDangerPageParam) {
        return CommonResult.data(epcFacilityDangerService.page(epcFacilityDangerPageParam));
    }

    /**
     * 添加EPC-危险源识别
     *
     * @author yong.ye
     * @date  2023/09/05 14:51
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加EPC-危险源识别")
    @CommonLog("添加EPC-危险源识别")
//    @SaCheckPermission("/biz/epc/danger/add")
    @PostMapping("/biz/epc/danger/add")
    public CommonResult<String> add(@RequestBody @Valid EpcFacilityDangerAddParam epcFacilityDangerAddParam) {
        epcFacilityDangerService.add(epcFacilityDangerAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑EPC-危险源识别
     *
     * @author yong.ye
     * @date  2023/09/05 14:51
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑EPC-危险源识别")
    @CommonLog("编辑EPC-危险源识别")
//    @SaCheckPermission("/biz/epc/danger/edit")
    @PostMapping("/biz/epc/danger/edit")
    public CommonResult<String> edit(@RequestBody @Valid EpcFacilityDangerEditParam epcFacilityDangerEditParam) {
        epcFacilityDangerService.edit(epcFacilityDangerEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除EPC-危险源识别
     *
     * @author yong.ye
     * @date  2023/09/05 14:51
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除EPC-危险源识别")
    @CommonLog("删除EPC-危险源识别")
//    @SaCheckPermission("/biz/epc/danger/delete")
    @PostMapping("/biz/epc/danger/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EpcFacilityDangerIdParam> epcFacilityDangerIdParamList) {
        epcFacilityDangerService.delete(epcFacilityDangerIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取EPC-危险源识别详情
     *
     * @author yong.ye
     * @date  2023/09/05 14:51
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取EPC-危险源识别详情")
//    @SaCheckPermission("/biz/epc/danger/detail")
    @GetMapping("/biz/epc/danger/detail")
    public CommonResult<EpcFacilityDanger> detail(@Valid EpcFacilityDangerIdParam epcFacilityDangerIdParam) {
        return CommonResult.data(epcFacilityDangerService.detail(epcFacilityDangerIdParam));
    }
}
