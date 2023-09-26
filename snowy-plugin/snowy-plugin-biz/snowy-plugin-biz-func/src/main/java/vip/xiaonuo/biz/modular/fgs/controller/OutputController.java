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
package vip.xiaonuo.biz.modular.fgs.controller;

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
import vip.xiaonuo.biz.modular.fgs.param.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.fgs.entity.Output;
import vip.xiaonuo.biz.modular.fgs.service.OutputService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-FGS-建筑业产值明细表控制器
 *
 * @author yong.ye
 * @date  2023/08/02 10:30
 */
@Api(tags = "业务表-FGS-建筑业产值明细表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class OutputController {

    @Resource
    private OutputService outputService;

    /**
     * 获取业务表-FGS-建筑业产值明细表分页
     *
     * @author yong.ye
     * @date  2023/08/02 10:30
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-FGS-建筑业产值明细表分页")
    @GetMapping("/biz/fgs/output/page")
    public CommonResult<Page<Output>> page(OutputPageParam outputPageParam) {
        return CommonResult.data(outputService.page(outputPageParam));
    }

    /**
     * 添加业务表-FGS-建筑业产值明细表
     *
     * @author yong.ye
     * @date  2023/08/02 10:30
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-FGS-建筑业产值明细表")
    @CommonLog("添加业务表-FGS-建筑业产值明细表")
    @PostMapping("/biz/fgs/output/add")
    public CommonResult<String> add(@RequestBody @Valid OutputAddParam outputAddParam) {
        outputService.add(outputAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-FGS-建筑业产值明细表
     *
     * @author yong.ye
     * @date  2023/08/02 10:30
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-FGS-建筑业产值明细表")
    @CommonLog("编辑业务表-FGS-建筑业产值明细表")
    @PostMapping("/biz/fgs/output/edit")
    public CommonResult<String> edit(@RequestBody @Valid OutputEditParam outputEditParam) {
        outputService.edit(outputEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-FGS-建筑业产值明细表
     *
     * @author yong.ye
     * @date  2023/08/02 10:30
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-FGS-建筑业产值明细表")
    @CommonLog("删除业务表-FGS-建筑业产值明细表")
    @PostMapping("/biz/fgs/output/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<OutputIdParam> outputIdParamList) {
        outputService.delete(outputIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-FGS-建筑业产值明细表详情
     *
     * @author yong.ye
     * @date  2023/08/02 10:30
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-FGS-建筑业产值明细表详情")
    @GetMapping("/biz/fgs/output/detail")
    public CommonResult<Output> detail(@Valid OutputIdParam outputIdParam) {
        return CommonResult.data(outputService.detail(outputIdParam));
    }

    /**
     * 业务表-FGS-建筑业产值明细表 (批量处理：保存 - 新增|编辑)
     *
     * @author yong.ye
     * @date  2023/08/02 10:30
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("业务表-FGS-建筑业产值明细表 (批量处理：保存 - 新增|编辑)")
    @CommonLog("业务表-FGS-建筑业产值明细表 (批量处理：保存 - 新增|编辑)")
    @PostMapping("/biz/fgs/output/batchSave")
    public CommonResult<String> batchSave(@RequestBody @Valid BatchOutputParam batchOutputParam) {
        outputService.batchSave(batchOutputParam);
        return CommonResult.ok();
    }
}
