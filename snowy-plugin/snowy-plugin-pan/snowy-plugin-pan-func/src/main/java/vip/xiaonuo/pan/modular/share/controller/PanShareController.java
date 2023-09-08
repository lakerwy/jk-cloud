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
package vip.xiaonuo.pan.modular.share.controller;

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
import vip.xiaonuo.pan.modular.share.entity.PanShare;
import vip.xiaonuo.pan.modular.share.param.*;
import vip.xiaonuo.pan.modular.share.param.dto.ShareFileDto;
import vip.xiaonuo.pan.modular.share.service.PanShareService;
import vip.xiaonuo.pan.modular.share.vo.ShareFileVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 网盘-分享表控制器
 *
 * @author yong.ye
 * @date  2023/09/07 15:55
 */
@Api(tags = "网盘-分享表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanShareController {

    @Resource
    private PanShareService panShareService;

    /**
     * 获取网盘-分享表分页
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取网盘-分享表分页")
//    @SaCheckPermission("/pan/share/page")
    @GetMapping("/pan/share/page")
    public CommonResult<Page<PanShare>> page(PanSharePageParam panSharePageParam) {
        return CommonResult.data(panShareService.page(panSharePageParam));
    }

    /**
     * 添加网盘-分享表
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加网盘-分享表")
    @CommonLog("添加网盘-分享表")
//    @SaCheckPermission("/pan/share/add")
    @PostMapping("/pan/share/add")
    public CommonResult<String> add(@RequestBody @Valid PanShareAddParam panShareAddParam) {
        panShareService.add(panShareAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑网盘-分享表
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑网盘-分享表")
    @CommonLog("编辑网盘-分享表")
//    @SaCheckPermission("/pan/share/edit")
    @PostMapping("/pan/share/edit")
    public CommonResult<String> edit(@RequestBody @Valid PanShareEditParam panShareEditParam) {
        panShareService.edit(panShareEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除网盘-分享表
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除网盘-分享表")
    @CommonLog("删除网盘-分享表")
//    @SaCheckPermission("/pan/share/delete")
    @PostMapping("/pan/share/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PanShareIdParam> panShareIdParamList) {
        panShareService.delete(panShareIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取网盘-分享表详情
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取网盘-分享表详情")
//    @SaCheckPermission("/pan/share/detail")
    @GetMapping("/pan/share/detail")
    public CommonResult<PanShare> detail(@Valid PanShareIdParam panShareIdParam) {
        return CommonResult.data(panShareService.detail(panShareIdParam));
    }


    /**
     * 网盘-分享-分享文件
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("网盘-分享-分享文件")
    @CommonLog("网盘-分享-分享文件")
    @PostMapping("/pan/share/shareFile")
    public CommonResult<ShareFileVo> shareFile(@RequestBody @Valid ShareFileDto shareFileDto) {
        return CommonResult.data(panShareService.shareFile(shareFileDto));
    }
}
