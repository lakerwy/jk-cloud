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
package vip.xiaonuo.pan.modular.uploadTaskDetail.controller;

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
import vip.xiaonuo.pan.modular.uploadTaskDetail.entity.PanUploadTaskDetail;
import vip.xiaonuo.pan.modular.uploadTaskDetail.param.PanUploadTaskDetailAddParam;
import vip.xiaonuo.pan.modular.uploadTaskDetail.param.PanUploadTaskDetailEditParam;
import vip.xiaonuo.pan.modular.uploadTaskDetail.param.PanUploadTaskDetailIdParam;
import vip.xiaonuo.pan.modular.uploadTaskDetail.param.PanUploadTaskDetailPageParam;
import vip.xiaonuo.pan.modular.uploadTaskDetail.service.PanUploadTaskDetailService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 云盘-上传任务详情表控制器
 *
 * @author mayike
 * @date  2023/09/08 16:35
 */
@Api(tags = "云盘-上传任务详情表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PanUploadTaskDetailController {

    @Resource
    private PanUploadTaskDetailService panUploadTaskDetailService;

    /**
     * 获取云盘-上传任务详情表分页
     *
     * @author mayike
     * @date  2023/09/08 16:35
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取云盘-上传任务详情表分页")
    @SaCheckPermission("/pan/uploadTaskDetail/page")
    @GetMapping("/pan/uploadTaskDetail/page")
    public CommonResult<Page<PanUploadTaskDetail>> page(PanUploadTaskDetailPageParam panUploadTaskDetailPageParam) {
        return CommonResult.data(panUploadTaskDetailService.page(panUploadTaskDetailPageParam));
    }

    /**
     * 添加云盘-上传任务详情表
     *
     * @author mayike
     * @date  2023/09/08 16:35
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加云盘-上传任务详情表")
    @CommonLog("添加云盘-上传任务详情表")
    @SaCheckPermission("/pan/uploadTaskDetail/add")
    @PostMapping("/pan/uploadTaskDetail/add")
    public CommonResult<String> add(@RequestBody @Valid PanUploadTaskDetailAddParam panUploadTaskDetailAddParam) {
        panUploadTaskDetailService.add(panUploadTaskDetailAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑云盘-上传任务详情表
     *
     * @author mayike
     * @date  2023/09/08 16:35
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑云盘-上传任务详情表")
    @CommonLog("编辑云盘-上传任务详情表")
    @SaCheckPermission("/pan/uploadTaskDetail/edit")
    @PostMapping("/pan/uploadTaskDetail/edit")
    public CommonResult<String> edit(@RequestBody @Valid PanUploadTaskDetailEditParam panUploadTaskDetailEditParam) {
        panUploadTaskDetailService.edit(panUploadTaskDetailEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除云盘-上传任务详情表
     *
     * @author mayike
     * @date  2023/09/08 16:35
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除云盘-上传任务详情表")
    @CommonLog("删除云盘-上传任务详情表")
    @SaCheckPermission("/pan/uploadTaskDetail/delete")
    @PostMapping("/pan/uploadTaskDetail/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PanUploadTaskDetailIdParam> panUploadTaskDetailIdParamList) {
        panUploadTaskDetailService.delete(panUploadTaskDetailIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取云盘-上传任务详情表详情
     *
     * @author mayike
     * @date  2023/09/08 16:35
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取云盘-上传任务详情表详情")
    @SaCheckPermission("/pan/uploadTaskDetail/detail")
    @GetMapping("/pan/uploadTaskDetail/detail")
    public CommonResult<PanUploadTaskDetail> detail(@Valid PanUploadTaskDetailIdParam panUploadTaskDetailIdParam) {
        return CommonResult.data(panUploadTaskDetailService.detail(panUploadTaskDetailIdParam));
    }
}
