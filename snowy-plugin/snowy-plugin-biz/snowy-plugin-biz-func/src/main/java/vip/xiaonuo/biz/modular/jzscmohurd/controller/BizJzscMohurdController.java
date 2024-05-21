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
package vip.xiaonuo.biz.modular.jzscmohurd.controller;

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
import vip.xiaonuo.biz.modular.jzscmohurd.entity.BizJzscMohurd;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdAddParam;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdEditParam;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdIdParam;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdPageParam;
import vip.xiaonuo.biz.modular.jzscmohurd.service.BizJzscMohurdService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * BIZ_JZSC_MOHURD控制器
 *
 * @author mayike
 * @date  2024/05/20 17:18
 */
@Api(tags = "BIZ_JZSC_MOHURD控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizJzscMohurdController {

    @Resource
    private BizJzscMohurdService bizJzscMohurdService;

    /**
     * 获取BIZ_JZSC_MOHURD分页
     *
     * @author mayike
     * @date  2024/05/20 17:18
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取BIZ_JZSC_MOHURD分页")
//    @SaCheckPermission("/biz/jzscmohurd/page")
    @GetMapping("/biz/jzscmohurd/page")
    public CommonResult<Page<BizJzscMohurd>> page(BizJzscMohurdPageParam bizJzscMohurdPageParam) {
        return CommonResult.data(bizJzscMohurdService.page(bizJzscMohurdPageParam));
    }

    /**
     * 添加BIZ_JZSC_MOHURD
     *
     * @author mayike
     * @date  2024/05/20 17:18
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加BIZ_JZSC_MOHURD")
    @CommonLog("添加BIZ_JZSC_MOHURD")
//    @SaCheckPermission("/biz/jzscmohurd/add")
    @PostMapping("/biz/jzscmohurd/add")
    public CommonResult<String> add(@RequestBody @Valid BizJzscMohurdAddParam bizJzscMohurdAddParam) {
        bizJzscMohurdService.add(bizJzscMohurdAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑BIZ_JZSC_MOHURD
     *
     * @author mayike
     * @date  2024/05/20 17:18
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑BIZ_JZSC_MOHURD")
    @CommonLog("编辑BIZ_JZSC_MOHURD")
//    @SaCheckPermission("/biz/jzscmohurd/edit")
    @PostMapping("/biz/jzscmohurd/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizJzscMohurdEditParam bizJzscMohurdEditParam) {
        bizJzscMohurdService.edit(bizJzscMohurdEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除BIZ_JZSC_MOHURD
     *
     * @author mayike
     * @date  2024/05/20 17:18
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除BIZ_JZSC_MOHURD")
    @CommonLog("删除BIZ_JZSC_MOHURD")
//    @SaCheckPermission("/biz/jzscmohurd/delete")
    @PostMapping("/biz/jzscmohurd/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizJzscMohurdIdParam> bizJzscMohurdIdParamList) {
        bizJzscMohurdService.delete(bizJzscMohurdIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取BIZ_JZSC_MOHURD详情
     *
     * @author mayike
     * @date  2024/05/20 17:18
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取BIZ_JZSC_MOHURD详情")
//    @SaCheckPermission("/biz/jzscmohurd/detail")
    @GetMapping("/biz/jzscmohurd/detail")
    public CommonResult<BizJzscMohurd> detail(@Valid BizJzscMohurdIdParam bizJzscMohurdIdParam) {
        return CommonResult.data(bizJzscMohurdService.detail(bizJzscMohurdIdParam));
    }
}
