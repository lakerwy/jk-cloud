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
package vip.xiaonuo.pan.modular.pictureFile.controller;

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
import vip.xiaonuo.pan.modular.pictureFile.entity.PanPictureFile;
import vip.xiaonuo.pan.modular.pictureFile.param.PanPictureFileAddParam;
import vip.xiaonuo.pan.modular.pictureFile.param.PanPictureFileEditParam;
import vip.xiaonuo.pan.modular.pictureFile.param.PanPictureFileIdParam;
import vip.xiaonuo.pan.modular.pictureFile.param.PanPictureFilePageParam;
import vip.xiaonuo.pan.modular.pictureFile.service.PanPictureFileService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 云盘-图片文件控制器
 *
 * @author mayike
 * @date  2023/09/08 16:16
 */
@Api(tags = "云盘-图片文件控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanPictureFileController {

    @Resource
    private PanPictureFileService panPictureFileService;

    /**
     * 获取云盘-图片文件分页
     *
     * @author mayike
     * @date  2023/09/08 16:16
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取云盘-图片文件分页")
    @SaCheckPermission("/pan/pictureFile/page")
    @GetMapping("/pan/pictureFile/page")
    public CommonResult<Page<PanPictureFile>> page(PanPictureFilePageParam panPictureFilePageParam) {
        return CommonResult.data(panPictureFileService.page(panPictureFilePageParam));
    }

    /**
     * 添加云盘-图片文件
     *
     * @author mayike
     * @date  2023/09/08 16:16
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加云盘-图片文件")
    @CommonLog("添加云盘-图片文件")
    @SaCheckPermission("/pan/pictureFile/add")
    @PostMapping("/pan/pictureFile/add")
    public CommonResult<String> add(@RequestBody @Valid PanPictureFileAddParam panPictureFileAddParam) {
        panPictureFileService.add(panPictureFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑云盘-图片文件
     *
     * @author mayike
     * @date  2023/09/08 16:16
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑云盘-图片文件")
    @CommonLog("编辑云盘-图片文件")
    @SaCheckPermission("/pan/pictureFile/edit")
    @PostMapping("/pan/pictureFile/edit")
    public CommonResult<String> edit(@RequestBody @Valid PanPictureFileEditParam panPictureFileEditParam) {
        panPictureFileService.edit(panPictureFileEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除云盘-图片文件
     *
     * @author mayike
     * @date  2023/09/08 16:16
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除云盘-图片文件")
    @CommonLog("删除云盘-图片文件")
    @SaCheckPermission("/pan/pictureFile/delete")
    @PostMapping("/pan/pictureFile/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PanPictureFileIdParam> panPictureFileIdParamList) {
        panPictureFileService.delete(panPictureFileIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取云盘-图片文件详情
     *
     * @author mayike
     * @date  2023/09/08 16:16
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取云盘-图片文件详情")
    @SaCheckPermission("/pan/pictureFile/detail")
    @GetMapping("/pan/pictureFile/detail")
    public CommonResult<PanPictureFile> detail(@Valid PanPictureFileIdParam panPictureFileIdParam) {
        return CommonResult.data(panPictureFileService.detail(panPictureFileIdParam));
    }
}
