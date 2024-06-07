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
package vip.xiaonuo.bp.modular.recruit.supplier.controller;

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
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierEditParam;
import vip.xiaonuo.bp.modular.recruit.supplier.service.BpSupplierService;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierReturnVo;
import vip.xiaonuo.bp.modular.recruit.supplier.vo.BpSupplierVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierAddParam;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierIdParam;
import vip.xiaonuo.bp.modular.recruit.supplier.param.BpSupplierPageParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 供应商基本信息表控制器
 *
 * @author hxw
 * @date  2024/04/17 15:18
 */
@Api(tags = "供应商基本信息表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BpSupplierController {

    @Resource
    private BpSupplierService bpSupplierService;

    /**
     * 获取供应商基本信息表分页
     *
     * @author hxw
     * @date  2024/04/17 15:18
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取供应商基本信息表分页")
    //@SaCheckPermission("/bp/supplier/page")
    @PostMapping("/bp/supplier/page")
    public CommonResult<Page<BpSupplierVo>> page(@RequestBody @Valid BpSupplierPageParam bpSupplierPageParam) {
        return CommonResult.data(bpSupplierService.page(bpSupplierPageParam));
    }

    /**
     * 添加供应商基本信息表
     *
     * @author hxw
     * @date  2024/04/17 15:18
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加供应商基本信息表")
    @CommonLog("添加供应商基本信息表")
    //@SaCheckPermission("/bp/supplier/add")
    @PostMapping("/bp/supplier/add")
    public CommonResult<String> add(@RequestBody @Valid BpSupplierAddParam bpSupplierAddParam) {
        bpSupplierService.add(bpSupplierAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑供应商黑名单信息
     *
     * @author hxw
     * @date  2024/04/17 15:18
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑供应商黑名单信息")
    @CommonLog("编辑供应商黑名单信息")
    //@SaCheckPermission("/bp/supplier/blackEdit")
    @PostMapping("/bp/supplier/blackEdit")
    public CommonResult<String> blackEdit(@RequestBody @Valid BpSupplierEditParam bpSupplierEditParam) {
        bpSupplierService.blackEdit(bpSupplierEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除供应商基本信息表
     *
     * @author hxw
     * @date  2024/04/17 15:18
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除供应商基本信息表")
    @CommonLog("删除供应商基本信息表")
    //@SaCheckPermission("/bp/supplier/delete")
    @PostMapping("/bp/supplier/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BpSupplierIdParam> bpSupplierIdParamList) {
        bpSupplierService.delete(bpSupplierIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取供应商基本信息表详情
     *
     * @author hxw
     * @date  2024/04/17 15:18
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取供应商基本信息表详情")
    //@SaCheckPermission("/bp/supplier/detail")
    @GetMapping("/bp/supplier/detail")
    public CommonResult<BpSupplierReturnVo> detail(@Valid BpSupplierIdParam bpSupplierIdParam) {
        return CommonResult.data(bpSupplierService.detail(bpSupplierIdParam));
    }

    /**
     * 获取供应商公司名称
     * @author hxw
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取供应商公司名称")
    //@SaCheckPermission("/bp/supplier/getCompanyName")
    @PostMapping("/bp/supplier/getCompanyName")
    public CommonResult<Boolean> getCompanyName(@RequestBody @Valid BpSupplierPageParam bpSupplierPageParam) {
        return CommonResult.data(bpSupplierService.getCompanyName(bpSupplierPageParam.getCompanyName()));
    }
}
