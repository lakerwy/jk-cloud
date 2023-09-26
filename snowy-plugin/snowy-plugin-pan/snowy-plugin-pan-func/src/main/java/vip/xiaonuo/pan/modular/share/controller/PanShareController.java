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

import com.baomidou.mybatisplus.core.metadata.IPage;
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
import vip.xiaonuo.pan.modular.share.param.dto.*;
import vip.xiaonuo.pan.modular.share.service.PanShareService;
import vip.xiaonuo.pan.modular.share.vo.ShareFileVo;
import vip.xiaonuo.pan.modular.share.vo.ShareListVo;
import vip.xiaonuo.pan.modular.share.vo.ShareTypeVo;

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
     * 网盘-分享-分享文件
     *
     * 分享文件统一接口
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("网盘-分享-分享文件（分享文件统一接口）")
    @CommonLog("网盘-分享-分享文件（分享文件统一接口）")
    @PostMapping("/pan/share/shareFile")
    public CommonResult<ShareFileVo> shareFile(@RequestBody @Valid ShareFileDto shareFileDto) {
        return CommonResult.data(panShareService.shareFile(shareFileDto));
    }

    /**
     * 网盘-分享-保存分享文件
     *
     * 用来将别人分享的文件保存到自己的网盘中
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("网盘-分享-保存分享文件（用来将别人分享的文件保存到自己的网盘中）")
    @CommonLog("网盘-分享-分享文件（用来将别人分享的文件保存到自己的网盘中）")
    @PostMapping("/pan/share/saveShareFile")
    public CommonResult<String> saveShareFile(@RequestBody @Valid SaveShareFileDto saveShareFileDto) {
        panShareService.saveShareFile(saveShareFileDto);
        return CommonResult.ok();
    }


    /**
     * 网盘-分享-查看已分享列表
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("网盘-分享-查看已分享列表")
    @GetMapping("/pan/share/shareList")
    public CommonResult<IPage<ShareListVo>> shareList(@Valid PanSharePageParam panSharePageParam) {
        return CommonResult.data(panShareService.shareList(panSharePageParam));
    }

    /**
     * 网盘-分享-分享文件列表
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("网盘-分享-分享文件列表")
    @GetMapping("/pan/share/shareFileList")
    public CommonResult<IPage<ShareListVo>> shareFileList(@Valid ShareFilePageDto shareFilePageDto) {
        return CommonResult.data(panShareService.shareFileList(shareFilePageDto));
    }

    /**
     * 网盘-分享-分享 (获取分享类型)
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("网盘-分享-分享(获取分享类型)")
    @GetMapping("/pan/share/shareType")
    public CommonResult<ShareTypeVo> shareType(@Valid ShareParam shareParam) {
        return CommonResult.data(panShareService.shareType(shareParam));
    }

    /**
     * 网盘-分享-分享 (校验提取码)
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("网盘-分享-分享(校验提取码)")
    @GetMapping("/pan/share/checkExtractionCode")
    public CommonResult<Boolean> checkExtractionCode(@Valid ShareExtractionCodeDto shareExtractionCodeDto) {
        return CommonResult.data(panShareService.checkExtractionCode(shareExtractionCodeDto));
    }


    /**
     * 网盘-分享-分享 (校验分享过期时间)
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("网盘-分享-分享(校验分享过期时间)")
    @GetMapping("/pan/share/checkEndTime")
    public CommonResult<Boolean> checkEndTime(@Valid ShareParam shareParam) {
        return CommonResult.data(panShareService.checkEndTime(shareParam));
    }

}
