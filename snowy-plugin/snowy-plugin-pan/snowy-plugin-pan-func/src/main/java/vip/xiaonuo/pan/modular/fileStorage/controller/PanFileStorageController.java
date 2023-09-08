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
package vip.xiaonuo.pan.modular.fileStorage.controller;

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
import vip.xiaonuo.pan.modular.fileStorage.entity.PanFileStorage;
import vip.xiaonuo.pan.modular.fileStorage.param.PanFileStorageAddParam;
import vip.xiaonuo.pan.modular.fileStorage.param.PanFileStorageEditParam;
import vip.xiaonuo.pan.modular.fileStorage.param.PanFileStorageIdParam;
import vip.xiaonuo.pan.modular.fileStorage.param.PanFileStoragePageParam;
import vip.xiaonuo.pan.modular.fileStorage.service.PanFileStorageService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 存储实体类控制器
 *
 * @author mayike
 * @date  2023/09/07 14:57
 */
@Api(tags = "存储实体类控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanFileStorageController {

    @Resource
    private PanFileStorageService panFileStorageService;

    /**
     * 获取存储实体类分页
     *
     * @author mayike
     * @date  2023/09/07 14:57
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取存储实体类分页")
    @SaCheckPermission("/pan/fileStorage/page")
    @GetMapping("/pan/fileStorage/page")
    public CommonResult<Page<PanFileStorage>> page(PanFileStoragePageParam panFileStoragePageParam) {
        return CommonResult.data(panFileStorageService.page(panFileStoragePageParam));
    }

    /**
     * 添加存储实体类
     *
     * @author mayike
     * @date  2023/09/07 14:57
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加存储实体类")
    @CommonLog("添加存储实体类")
    @SaCheckPermission("/pan/fileStorage/add")
    @PostMapping("/pan/fileStorage/add")
    public CommonResult<String> add(@RequestBody @Valid PanFileStorageAddParam panFileStorageAddParam) {
        panFileStorageService.add(panFileStorageAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑存储实体类
     *
     * @author mayike
     * @date  2023/09/07 14:57
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑存储实体类")
    @CommonLog("编辑存储实体类")
    @SaCheckPermission("/pan/fileStorage/edit")
    @PostMapping("/pan/fileStorage/edit")
    public CommonResult<String> edit(@RequestBody @Valid PanFileStorageEditParam panFileStorageEditParam) {
        panFileStorageService.edit(panFileStorageEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除存储实体类
     *
     * @author mayike
     * @date  2023/09/07 14:57
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除存储实体类")
    @CommonLog("删除存储实体类")
    @SaCheckPermission("/pan/fileStorage/delete")
    @PostMapping("/pan/fileStorage/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PanFileStorageIdParam> panFileStorageIdParamList) {
        panFileStorageService.delete(panFileStorageIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取存储实体类详情
     *
     * @author mayike
     * @date  2023/09/07 14:57
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取存储实体类详情")
    @SaCheckPermission("/pan/fileStorage/detail")
    @GetMapping("/pan/fileStorage/detail")
    public CommonResult<PanFileStorage> detail(@Valid PanFileStorageIdParam panFileStorageIdParam) {
        return CommonResult.data(panFileStorageService.detail(panFileStorageIdParam));
    }
}
