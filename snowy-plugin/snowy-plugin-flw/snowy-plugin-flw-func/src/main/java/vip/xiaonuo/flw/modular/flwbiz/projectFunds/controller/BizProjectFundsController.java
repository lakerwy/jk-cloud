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
package vip.xiaonuo.flw.modular.flwbiz.projectFunds.controller;

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
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.param.BizProjectFundsAddParam;
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.param.BizProjectFundsEditParam;
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.param.BizProjectFundsIdParam;
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.param.BizProjectFundsPageParam;
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.service.BizProjectFundsService;
import vip.xiaonuo.flw.modular.flwbiz.projectFunds.entity.BizProjectFunds;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-工程款拨付单控制器
 *
 * @author mayike
 * @date  2023/06/19 17:26
 */
@Api(tags = "业务表-工程款拨付单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizProjectFundsController {

    @Resource
    private BizProjectFundsService bizProjectFundsService;

    /**
     * 获取业务表-工程款拨付单分页
     *
     * @author mayike
     * @date  2023/06/19 17:26
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-工程款拨付单分页")
//    @SaCheckPermission("/flw/projectFunds/page")
    @GetMapping("/flw/projectFunds/page")
    public CommonResult<Page<BizProjectFunds>> page(BizProjectFundsPageParam bizProjectFundsPageParam) {
        return CommonResult.data(bizProjectFundsService.page(bizProjectFundsPageParam));
    }

    /**
     * 添加业务表-工程款拨付单
     *
     * @author mayike
     * @date  2023/06/19 17:26
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-工程款拨付单")
    @CommonLog("添加业务表-工程款拨付单")
//    @SaCheckPermission("/flw/projectFunds/add")
    @PostMapping("/flw/projectFunds/add")
    public CommonResult<String> add(@RequestBody @Valid BizProjectFundsAddParam bizProjectFundsAddParam) {
        bizProjectFundsService.add(bizProjectFundsAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-工程款拨付单
     *
     * @author mayike
     * @date  2023/06/19 17:26
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-工程款拨付单")
    @CommonLog("编辑业务表-工程款拨付单")
//    @SaCheckPermission("/flw/projectFunds/edit")
    @PostMapping("/flw/projectFunds/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizProjectFundsEditParam bizProjectFundsEditParam) {
        bizProjectFundsService.edit(bizProjectFundsEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-工程款拨付单
     *
     * @author mayike
     * @date  2023/06/19 17:26
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-工程款拨付单")
    @CommonLog("删除业务表-工程款拨付单")
//    @SaCheckPermission("/flw/projectFunds/delete")
    @PostMapping("/flw/projectFunds/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizProjectFundsIdParam> bizProjectFundsIdParamList) {
        bizProjectFundsService.delete(bizProjectFundsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-工程款拨付单详情
     *
     * @author mayike
     * @date  2023/06/19 17:26
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-工程款拨付单详情")
//    @SaCheckPermission("/flw/projectFunds/detail")
    @GetMapping("/flw/projectFunds/detail")
    public CommonResult<BizProjectFunds> detail(@Valid BizProjectFundsIdParam bizProjectFundsIdParam) {
        return CommonResult.data(bizProjectFundsService.detail(bizProjectFundsIdParam));
    }
}
