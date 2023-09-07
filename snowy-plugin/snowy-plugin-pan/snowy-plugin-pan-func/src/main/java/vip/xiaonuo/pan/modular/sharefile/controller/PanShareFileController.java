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
package vip.xiaonuo.pan.modular.sharefile.controller;

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
import vip.xiaonuo.pan.modular.sharefile.entity.PanShareFile;
import vip.xiaonuo.pan.modular.sharefile.param.*;
import vip.xiaonuo.pan.modular.sharefile.service.PanShareFileService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 网盘-文件分享表控制器
 *
 * @author yong.ye
 * @date  2023/09/07 15:56
 */
@Api(tags = "网盘-文件分享表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanShareFileController {

    @Resource
    private PanShareFileService panShareFileService;

    /**
     * 获取网盘-文件分享表分页
     *
     * @author yong.ye
     * @date  2023/09/07 15:56
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取网盘-文件分享表分页")
//    @SaCheckPermission("/pan/share/file/page")
    @GetMapping("/pan/share/file/page")
    public CommonResult<Page<PanShareFile>> page(PanShareFilePageParam panShareFilePageParam) {
        return CommonResult.data(panShareFileService.page(panShareFilePageParam));
    }

    /**
     * 添加网盘-文件分享表
     *
     * @author yong.ye
     * @date  2023/09/07 15:56
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加网盘-文件分享表")
    @CommonLog("添加网盘-文件分享表")
//    @SaCheckPermission("/pan/share/file/add")
    @PostMapping("/pan/share/file/add")
    public CommonResult<String> add(@RequestBody @Valid PanShareFileAddParam panShareFileAddParam) {
        panShareFileService.add(panShareFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑网盘-文件分享表
     *
     * @author yong.ye
     * @date  2023/09/07 15:56
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑网盘-文件分享表")
    @CommonLog("编辑网盘-文件分享表")
//    @SaCheckPermission("/pan/share/file/edit")
    @PostMapping("/pan/share/file/edit")
    public CommonResult<String> edit(@RequestBody @Valid PanShareFileEditParam panShareFileEditParam) {
        panShareFileService.edit(panShareFileEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除网盘-文件分享表
     *
     * @author yong.ye
     * @date  2023/09/07 15:56
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除网盘-文件分享表")
    @CommonLog("删除网盘-文件分享表")
//    @SaCheckPermission("/pan/share/file/delete")
    @PostMapping("/pan/share/file/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PanShareFileIdParam> panShareFileIdParamList) {
        panShareFileService.delete(panShareFileIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取网盘-文件分享表详情
     *
     * @author yong.ye
     * @date  2023/09/07 15:56
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取网盘-文件分享表详情")
//    @SaCheckPermission("/pan/share/file/detail")
    @GetMapping("/pan/share/file/detail")
    public CommonResult<PanShareFile> detail(@Valid PanShareFileIdParam panShareFileIdParam) {
        return CommonResult.data(panShareFileService.detail(panShareFileIdParam));
    }
}
