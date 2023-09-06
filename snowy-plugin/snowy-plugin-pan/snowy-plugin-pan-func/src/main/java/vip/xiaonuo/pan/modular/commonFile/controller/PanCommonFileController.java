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
package vip.xiaonuo.pan.modular.commonFile.controller;

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
import vip.xiaonuo.pan.modular.commonFile.entity.PanCommonFile;
import vip.xiaonuo.pan.modular.commonFile.param.PanCommonFileAddParam;
import vip.xiaonuo.pan.modular.commonFile.param.PanCommonFileEditParam;
import vip.xiaonuo.pan.modular.commonFile.param.PanCommonFileIdParam;
import vip.xiaonuo.pan.modular.commonFile.param.PanCommonFilePageParam;
import vip.xiaonuo.pan.modular.commonFile.service.PanCommonFileService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 共享文件控制器
 *
 * @author mayike
 * @date  2023/09/06 21:07
 */
@Api(tags = "共享文件控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanCommonFileController {

    @Resource
    private PanCommonFileService panCommonFileService;

    /**
     * 获取共享文件分页
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取共享文件分页")
    @SaCheckPermission("/pan/commonFile/page")
    @GetMapping("/pan/commonFile/page")
    public CommonResult<Page<PanCommonFile>> page(PanCommonFilePageParam panCommonFilePageParam) {
        return CommonResult.data(panCommonFileService.page(panCommonFilePageParam));
    }

    /**
     * 添加共享文件
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加共享文件")
    @CommonLog("添加共享文件")
    @SaCheckPermission("/pan/commonFile/add")
    @PostMapping("/pan/commonFile/add")
    public CommonResult<String> add(@RequestBody @Valid PanCommonFileAddParam panCommonFileAddParam) {
        panCommonFileService.add(panCommonFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑共享文件
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑共享文件")
    @CommonLog("编辑共享文件")
    @SaCheckPermission("/pan/commonFile/edit")
    @PostMapping("/pan/commonFile/edit")
    public CommonResult<String> edit(@RequestBody @Valid PanCommonFileEditParam panCommonFileEditParam) {
        panCommonFileService.edit(panCommonFileEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除共享文件
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除共享文件")
    @CommonLog("删除共享文件")
    @SaCheckPermission("/pan/commonFile/delete")
    @PostMapping("/pan/commonFile/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PanCommonFileIdParam> panCommonFileIdParamList) {
        panCommonFileService.delete(panCommonFileIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取共享文件详情
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取共享文件详情")
    @SaCheckPermission("/pan/commonFile/detail")
    @GetMapping("/pan/commonFile/detail")
    public CommonResult<PanCommonFile> detail(@Valid PanCommonFileIdParam panCommonFileIdParam) {
        return CommonResult.data(panCommonFileService.detail(panCommonFileIdParam));
    }
}
