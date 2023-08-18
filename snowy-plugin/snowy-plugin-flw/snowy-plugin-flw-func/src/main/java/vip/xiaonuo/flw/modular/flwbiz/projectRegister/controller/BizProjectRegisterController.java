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
package vip.xiaonuo.flw.modular.flwbiz.projectRegister.controller;

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
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.entity.BizProjectRegister;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.param.*;
import vip.xiaonuo.flw.modular.flwbiz.projectRegister.service.BizProjectRegisterService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-立项报备控制器
 *
 * @author mayike
 * @date  2023/06/19 15:26
 */
@Api(tags = "业务表-信息报备控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizProjectRegisterController {

    @Resource
    private BizProjectRegisterService bizProjectRegisterService;

    /**
     * 获取业务表-立项报备分页
     *
     * @author mayike
     * @date  2023/06/19 15:26
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-信息报备分页")
    //@SaCheckPermission("/flw/projectRegister/page")
    @GetMapping("/flw/projectRegister/page")
    public CommonResult<Page<BizProjectRegister>> page(BizProjectRegisterPageParam bizProjectRegisterPageParam) {
        return CommonResult.data(bizProjectRegisterService.page(bizProjectRegisterPageParam));
    }

    /**
     * 添加业务表-立项报备
     *
     * @author mayike
     * @date  2023/06/19 15:26
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-信息报备")
    @CommonLog("添加业务表-信息报备")
//    @SaCheckPermission("/flw/projectRegister/add")
    @PostMapping("/flw/projectRegister/add")
    public CommonResult<String> add(@RequestBody @Valid BizProjectRegisterAddParam bizProjectRegisterAddParam) {
        bizProjectRegisterService.add(bizProjectRegisterAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-立项报备
     *
     * @author mayike
     * @date  2023/06/19 15:26
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-信息报备")
    @CommonLog("编辑业务表-信息报备")
//    @SaCheckPermission("/flw/projectRegister/edit")
    @PostMapping("/flw/projectRegister/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizProjectRegisterEditParam bizProjectRegisterEditParam) {
        bizProjectRegisterService.edit(bizProjectRegisterEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-立项报备
     *
     * @author mayike
     * @date  2023/06/19 15:26
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-信息报备")
    @CommonLog("删除业务表-信息报备")
//    @SaCheckPermission("/flw/projectRegister/delete")
    @PostMapping("/flw/projectRegister/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizProjectRegisterIdParam> bizProjectRegisterIdParamList) {
        bizProjectRegisterService.delete(bizProjectRegisterIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-立项报备详情
     *
     * @author mayike
     * @date  2023/06/19 15:26
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-信息报备详情")
//    @SaCheckPermission("/flw/projectRegister/detail")
    @GetMapping("/flw/projectRegister/detail")
    public CommonResult<BizProjectRegister> detail(@Valid BizProjectRegisterIdParam bizProjectRegisterIdParam) {
        return CommonResult.data(bizProjectRegisterService.detail(bizProjectRegisterIdParam));
    }

    @ApiOperationSupport(order = 7)
    @ApiOperation("修改业务表-信息状态情况")
//    @SaCheckPermission("/flw/projectRegister/updateStatus")
    @PostMapping("/flw/projectRegister/updateStatus")
    public CommonResult<String> updateStatus(BizProjectRegisterCommonEditParam bizProjectRegisterCommonEditParam){
        bizProjectRegisterService.updateStatus(bizProjectRegisterCommonEditParam);
        return CommonResult.ok();
    }
}
