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
package vip.xiaonuo.pan.modular.file.controller;

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
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.param.PanFileAddParam;
import vip.xiaonuo.pan.modular.file.param.PanFileEditParam;
import vip.xiaonuo.pan.modular.file.param.PanFileIdParam;
import vip.xiaonuo.pan.modular.file.param.PanFilePageParam;
import vip.xiaonuo.pan.modular.file.service.PanFileService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

/**
 * 文件控制器
 *
 * @author mayike
 * @date  2023/09/06 21:15
 */
@Api(tags = "文件控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanFileController {

    @Resource
    private PanFileService panFileService;

    /**
     * 获取文件分页
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取文件分页")
    @SaCheckPermission("/pan/file/page")
    @GetMapping("/pan/file/page")
    public CommonResult<Page<PanFile>> page(PanFilePageParam panFilePageParam) {
        return CommonResult.data(panFileService.page(panFilePageParam));
    }

    /**
     * 添加文件
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加文件")
    @CommonLog("添加文件")
    @SaCheckPermission("/pan/file/add")
    @PostMapping("/pan/file/add")
    public CommonResult<String> add(@RequestBody @Valid PanFileAddParam panFileAddParam) {
        panFileService.add(panFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑文件
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑文件")
    @CommonLog("编辑文件")
    @SaCheckPermission("/pan/file/edit")
    @PostMapping("/pan/file/edit")
    public CommonResult<String> edit(@RequestBody @Valid PanFileEditParam panFileEditParam) {
        panFileService.edit(panFileEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除文件
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除文件")
    @CommonLog("删除文件")
    @SaCheckPermission("/pan/file/delete")
    @PostMapping("/pan/file/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PanFileIdParam> panFileIdParamList) {
        panFileService.delete(panFileIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取文件详情
     *
     * @author mayike
     * @date  2023/09/06 21:15
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取文件详情")
    @SaCheckPermission("/pan/file/detail")
    @GetMapping("/pan/file/detail")
    public CommonResult<PanFile> detail(@Valid PanFileIdParam panFileIdParam) {
        return CommonResult.data(panFileService.detail(panFileIdParam));
    }
}
