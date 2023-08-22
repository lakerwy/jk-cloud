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
package vip.xiaonuo.biz.modular.mapBiz.MapRegister.controller;

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
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.entity.BizMapRegister;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterIdParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.service.BizMapRegisterService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterAddParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterEditParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterPageParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-出图登记记录表控制器
 *
 * @author mayike
 * @date  2023/07/03 11:46
 */
@Api(tags = "业务表-出图登记记录表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizMapRegisterController {

    @Resource
    private BizMapRegisterService bizMapRegisterService;

    /**
     * 获取业务表-出图登记记录表分页
     *
     * @author mayike
     * @date  2023/07/03 11:46
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-出图登记记录表分页")
    @SaCheckPermission("/biz/MapRegister/page")
    @GetMapping("/biz/MapRegister/page")
    public CommonResult<Page<BizMapRegister>> page(BizMapRegisterPageParam bizMapRegisterPageParam) {
        return CommonResult.data(bizMapRegisterService.page(bizMapRegisterPageParam));
    }

    /**
     * 添加业务表-出图登记记录表
     *
     * @author mayike
     * @date  2023/07/03 11:46
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-出图登记记录表")
    @CommonLog("添加业务表-出图登记记录表")
//    @SaCheckPermission("/biz/MapRegister/add")
    @PostMapping("/biz/MapRegister/add")
    public CommonResult<String> add(@RequestBody @Valid BizMapRegisterAddParam bizMapRegisterAddParam) {
        bizMapRegisterService.add(bizMapRegisterAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-出图登记记录表
     *
     * @author mayike
     * @date  2023/07/03 11:46
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-出图登记记录表")
    @CommonLog("编辑业务表-出图登记记录表")
//    @SaCheckPermission("/biz/MapRegister/edit")
    @PostMapping("/biz/MapRegister/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizMapRegisterEditParam bizMapRegisterEditParam) {
        bizMapRegisterService.edit(bizMapRegisterEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-出图登记记录表
     *
     * @author mayike
     * @date  2023/07/03 11:46
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-出图登记记录表")
    @CommonLog("删除业务表-出图登记记录表")
//    @SaCheckPermission("/biz/MapRegister/delete")
    @PostMapping("/biz/MapRegister/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizMapRegisterIdParam> bizMapRegisterIdParamList) {
        bizMapRegisterService.delete(bizMapRegisterIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-出图登记记录表详情
     *
     * @author mayike
     * @date  2023/07/03 11:46
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-出图登记记录表详情")
//    @SaCheckPermission("/biz/MapRegister/detail")
    @GetMapping("/biz/MapRegister/detail")
    public CommonResult<BizMapRegister> detail(@Valid BizMapRegisterIdParam bizMapRegisterIdParam) {
        return CommonResult.data(bizMapRegisterService.detail(bizMapRegisterIdParam));
    }
}
