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
package vip.xiaonuo.biz.modular.teambition.controller;

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
import vip.xiaonuo.biz.modular.teambition.entity.*;
import vip.xiaonuo.biz.modular.teambition.param.*;
import vip.xiaonuo.biz.modular.teambition.service.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 任务管理平台接口
 *
 * @author ljy
 * @date  2023/12/08 08:58
 */
@Api(tags = "任务管理平台接口")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizTaskGroupController {

    @Resource
    private BizTaskGroupService bizTaskGroupService;
    @Resource
    private BizTaskFileService bizTaskFileService;
    @Resource
    private BizTaskProjectService bizTaskProjectService;
    @Resource
    private BizTaskInfoService bizTaskInfoService;
    @Resource
    private BizTaskGroupResService bizTaskGroupResService;
    /**
     * 获取任务来源表分页
     *
     * @author ljy
     * @date  2023/12/08 08:58
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取任务来源表分页")
    //@SaCheckPermission("/biz/taskGroup/page")
    @GetMapping("/biz/taskGroup/page")
    public CommonResult<Page<BizTaskGroup>> page(BizTaskGroupPageParam bizTaskGroupPageParam) {
        return CommonResult.data(bizTaskGroupService.page(bizTaskGroupPageParam));
    }

    /**
     * 添加任务来源表
     *
     * @author ljy
     * @date  2023/12/08 08:58
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加任务来源表")
    @CommonLog("添加任务来源表")
    //@SaCheckPermission("/biz/taskGroup/add")
    @PostMapping("/biz/taskGroup/add")
    public CommonResult<String> add(@RequestBody @Valid BizTaskGroupAddParam bizTaskGroupAddParam) {
        bizTaskGroupService.add(bizTaskGroupAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑任务来源表
     *
     * @author ljy
     * @date  2023/12/08 08:58
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑任务来源表")
    @CommonLog("编辑任务来源表")
    //@SaCheckPermission("/biz/taskGroup/edit")
    @PostMapping("/biz/taskGroup/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizTaskGroupEditParam bizTaskGroupEditParam) {
        bizTaskGroupService.edit(bizTaskGroupEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除任务来源表
     *
     * @author ljy
     * @date  2023/12/08 08:58
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除任务来源表")
    @CommonLog("删除任务来源表")
    //@SaCheckPermission("/biz/taskGroup/delete")
    @PostMapping("/biz/taskGroup/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizTaskGroupIdParam> bizTaskGroupIdParamList) {
        bizTaskGroupService.delete(bizTaskGroupIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取任务来源表详情
     *
     * @author ljy
     * @date  2023/12/08 08:58
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取任务来源表详情")
    //@SaCheckPermission("/biz/taskGroup/detail")
    @GetMapping("/biz/taskGroup/detail")
    public CommonResult<BizTaskGroup> detail(@Valid BizTaskGroupIdParam bizTaskGroupIdParam) {
        return CommonResult.data(bizTaskGroupService.detail(bizTaskGroupIdParam));
    }

    /** 获取任务管理附件数据*/
    /**
     * 获取任务管理附件数据表分页
     *
     * @author ljy
     * @date  2023/12/11 10:57
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取任务管理附件数据表分页")
    //@SaCheckPermission("/biz/taskFile/page")
    @GetMapping("/biz/taskFile/page")
    public CommonResult<Page<BizTaskFile>> filePage(BizTaskFilePageParam bizTaskFilePageParam) {
        return CommonResult.data(bizTaskFileService.page(bizTaskFilePageParam));
    }

    /**
     * 添加任务管理附件数据表
     *
     * @author ljy
     * @date  2023/12/11 10:57
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("添加任务管理附件数据表")
    @CommonLog("添加任务管理附件数据表")
    //@SaCheckPermission("/biz/taskFile/add")
    @PostMapping("/biz/taskFile/add")
    public CommonResult<String> fileAdd(@RequestBody @Valid BizTaskFileAddParam bizTaskFileAddParam) {
        bizTaskFileService.add(bizTaskFileAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑任务管理附件数据表
     *
     * @author ljy
     * @date  2023/12/11 10:57
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("编辑任务管理附件数据表")
    @CommonLog("编辑任务管理附件数据表")
    //@SaCheckPermission("/biz/taskFile/edit")
    @PostMapping("/biz/taskFile/edit")
    public CommonResult<String> fileEdit(@RequestBody @Valid BizTaskFileEditParam bizTaskFileEditParam) {
        bizTaskFileService.edit(bizTaskFileEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除任务管理附件数据表
     *
     * @author ljy
     * @date  2023/12/11 10:57
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("删除任务管理附件数据表")
    @CommonLog("删除任务管理附件数据表")
    //@SaCheckPermission("/biz/taskFile/delete")
    @PostMapping("/biz/taskFile/delete")
    public CommonResult<String> fileDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<BizTaskFileIdParam> bizTaskFileIdParamList) {
        bizTaskFileService.delete(bizTaskFileIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取任务管理附件数据表详情
     *
     * @author ljy
     * @date  2023/12/11 10:57
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取任务管理附件数据表详情")
    //@SaCheckPermission("/biz/taskFile/detail")
    @GetMapping("/biz/taskFile/detail")
    public CommonResult<BizTaskFile> fileDetail(@Valid BizTaskFileIdParam bizTaskFileIdParam) {
        return CommonResult.data(bizTaskFileService.detail(bizTaskFileIdParam));
    }



    /** 获取任务项目数据接口*/
    /**
     * 获取任务项目表分页
     *
     * @author ljy
     * @date  2023/12/08 09:06
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("获取任务项目表分页")
    //@SaCheckPermission("/biz/taskProject/page")
    @GetMapping("/biz/taskProject/page")
    public CommonResult<Page<BizTaskProject>> projectPage(BizTaskProjectPageParam bizTaskProjectPageParam) {
        return CommonResult.data(bizTaskProjectService.page(bizTaskProjectPageParam));
    }

    /**
     * 添加任务项目表
     *
     * @author ljy
     * @date  2023/12/08 09:06
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("添加任务项目表")
    @CommonLog("添加任务项目表")
    //@SaCheckPermission("/biz/taskProject/add")
    @PostMapping("/biz/taskProject/add")
    public CommonResult<String> projectAdd(@RequestBody @Valid BizTaskProjectAddParam bizTaskProjectAddParam) {
        bizTaskProjectService.add(bizTaskProjectAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑任务项目表
     *
     * @author ljy
     * @date  2023/12/08 09:06
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("编辑任务项目表")
    @CommonLog("编辑任务项目表")
    //@SaCheckPermission("/biz/taskProject/edit")
    @PostMapping("/biz/taskProject/edit")
    public CommonResult<String> projectEdit(@RequestBody @Valid BizTaskProjectEditParam bizTaskProjectEditParam) {
        bizTaskProjectService.edit(bizTaskProjectEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除任务项目表
     *
     * @author ljy
     * @date  2023/12/08 09:06
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("删除任务项目表")
    @CommonLog("删除任务项目表")
    //@SaCheckPermission("/biz/taskProject/delete")
    @PostMapping("/biz/taskProject/delete")
    public CommonResult<String> projectDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<BizTaskProjectIdParam> bizTaskProjectIdParamList) {
        bizTaskProjectService.delete(bizTaskProjectIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取任务项目表详情
     *
     * @author ljy
     * @date  2023/12/08 09:06
     */
    @ApiOperationSupport(order = 15)
    @ApiOperation("获取任务项目表详情")
    //@SaCheckPermission("/biz/taskProject/detail")
    @GetMapping("/biz/taskProject/detail")
    public CommonResult<BizTaskProject> projectDetail(@Valid BizTaskProjectIdParam bizTaskProjectIdParam) {
        return CommonResult.data(bizTaskProjectService.detail(bizTaskProjectIdParam));
    }


    /** 获取任务详情数据接口*/
    /**
     * 获取任务主表分页
     *
     * @author ljy
     * @date  2023/12/08 09:03
     */
    @ApiOperationSupport(order = 16)
    @ApiOperation("获取任务主表分页")
    //@SaCheckPermission("/biz/taskInfo/page")
    @GetMapping("/biz/taskInfo/page")
    public CommonResult<Page<BizTaskInfo>> taskInfoPage(BizTaskInfoPageParam bizTaskInfoPageParam) {
        return CommonResult.data(bizTaskInfoService.page(bizTaskInfoPageParam));
    }

    /**
     * 添加任务主表
     *
     * @author ljy
     * @date  2023/12/08 09:03
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("添加任务主表")
    @CommonLog("添加任务主表")
    //@SaCheckPermission("/biz/taskInfo/add")
    @PostMapping("/biz/taskInfo/add")
    public CommonResult<String> taskInfoAdd(@RequestBody @Valid BizTaskInfoAddParam bizTaskInfoAddParam) {
        bizTaskInfoService.add(bizTaskInfoAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑任务主表
     *
     * @author ljy
     * @date  2023/12/08 09:03
     */
    @ApiOperationSupport(order = 18)
    @ApiOperation("编辑任务主表")
    @CommonLog("编辑任务主表")
    //@SaCheckPermission("/biz/taskInfo/edit")
    @PostMapping("/biz/taskInfo/edit")
    public CommonResult<String> taskInfoEdit(@RequestBody @Valid BizTaskInfoEditParam bizTaskInfoEditParam) {
        bizTaskInfoService.edit(bizTaskInfoEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除任务主表
     *
     * @author ljy
     * @date  2023/12/08 09:03
     */
    @ApiOperationSupport(order = 19)
    @ApiOperation("删除任务主表")
    @CommonLog("删除任务主表")
    //@SaCheckPermission("/biz/taskInfo/delete")
    @PostMapping("/biz/taskInfo/delete")
    public CommonResult<String> taskInfoEelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<BizTaskInfoIdParam> bizTaskInfoIdParamList) {
        bizTaskInfoService.delete(bizTaskInfoIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取任务主表详情
     *
     * @author ljy
     * @date  2023/12/08 09:03
     */
    @ApiOperationSupport(order = 20)
    @ApiOperation("获取任务主表详情")
    //@SaCheckPermission("/biz/taskInfo/detail")
    @GetMapping("/biz/taskInfo/detail")
    public CommonResult<BizTaskInfo> taskInfoDetail(@Valid BizTaskInfoIdParam bizTaskInfoIdParam) {
        return CommonResult.data(bizTaskInfoService.detail(bizTaskInfoIdParam));
    }



    /**  获取任务分组关联表数据接口*/
    /**
     * 获取任务分组关联表（人或部门）分页
     *
     * @author ljy
     * @date  2023/12/08 09:00
     */
    @ApiOperationSupport(order = 21)
    @ApiOperation("获取任务分组关联表（人或部门）分页")
    //@SaCheckPermission("/biz/taskGroupRes/page")
    @GetMapping("/biz/taskGroupRes/page")
    public CommonResult<Page<BizTaskGroupRes>> groupResPage(BizTaskGroupResPageParam bizTaskGroupResPageParam) {
        return CommonResult.data(bizTaskGroupResService.page(bizTaskGroupResPageParam));
    }

    /**
     * 添加任务分组关联表（人或部门）
     *
     * @author ljy
     * @date  2023/12/08 09:00
     */
    @ApiOperationSupport(order = 22)
    @ApiOperation("添加任务分组关联表（人或部门）")
    @CommonLog("添加任务分组关联表（人或部门）")
    //@SaCheckPermission("/biz/taskGroupRes/add")
    @PostMapping("/biz/taskGroupRes/add")
    public CommonResult<String> groupResAdd(@RequestBody @Valid BizTaskGroupResAddParam bizTaskGroupResAddParam) {
        bizTaskGroupResService.add(bizTaskGroupResAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑任务分组关联表（人或部门）
     *
     * @author ljy
     * @date  2023/12/08 09:00
     */
    @ApiOperationSupport(order = 23)
    @ApiOperation("编辑任务分组关联表（人或部门）")
    @CommonLog("编辑任务分组关联表（人或部门）")
    //@SaCheckPermission("/biz/taskGroupRes/edit")
    @PostMapping("/biz/taskGroupRes/edit")
    public CommonResult<String> groupResEdit(@RequestBody @Valid BizTaskGroupResEditParam bizTaskGroupResEditParam) {
        bizTaskGroupResService.edit(bizTaskGroupResEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除任务分组关联表（人或部门）
     *
     * @author ljy
     * @date  2023/12/08 09:00
     */
    @ApiOperationSupport(order = 24)
    @ApiOperation("删除任务分组关联表（人或部门）")
    @CommonLog("删除任务分组关联表（人或部门）")
    //@SaCheckPermission("/biz/taskGroupRes/delete")
    @PostMapping("/biz/taskGroupRes/delete")
    public CommonResult<String> groupResDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                               CommonValidList<BizTaskGroupResIdParam> bizTaskGroupResIdParamList) {
        bizTaskGroupResService.delete(bizTaskGroupResIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取任务分组关联表（人或部门）详情
     *
     * @author ljy
     * @date  2023/12/08 09:00
     */
    @ApiOperationSupport(order = 25)
    @ApiOperation("获取任务分组关联表（人或部门）详情")
    //@SaCheckPermission("/biz/taskGroupRes/detail")
    @GetMapping("/biz/taskGroupRes/detail")
    public CommonResult<BizTaskGroupRes> groupResDetail(@Valid BizTaskGroupResIdParam bizTaskGroupResIdParam) {
        return CommonResult.data(bizTaskGroupResService.detail(bizTaskGroupResIdParam));
    }
}
