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
import vip.xiaonuo.biz.modular.teambition.ThirdParam.ThirdTaskPageParam;
import vip.xiaonuo.biz.modular.teambition.entity.*;
import vip.xiaonuo.biz.modular.teambition.param.*;
import vip.xiaonuo.biz.modular.teambition.service.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

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
    private BizTaskInfoService bizTaskInfoService;
    /**
     * 获取任务来源表分页
     *
     * @author ljy
     * @date  2023/12/08 08:58
     */
    /*@ApiOperationSupport(order = 1)
    @ApiOperation("获取任务来源表分页")
    @GetMapping("/biz/taskGroup/page")
    public CommonResult<Page<BizTaskGroup>> page(BizTaskGroupPageParam bizTaskGroupPageParam) {
        return CommonResult.data(bizTaskGroupService.page(bizTaskGroupPageParam));
    }*/


    /** 获取任务管理附件数据*/
    /**
     * 获取任务管理附件数据表分页
     *
     * @author ljy
     * @date  2023/12/11 10:57
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取任务管理附件数据表分页")
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
    @GetMapping("/biz/taskFile/detail")
    public CommonResult<BizTaskFile> fileDetail(@Valid BizTaskFileIdParam bizTaskFileIdParam) {
        return CommonResult.data(bizTaskFileService.detail(bizTaskFileIdParam));
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
    @GetMapping("/biz/taskInfo/page")
    public CommonResult<Page<BizTaskInfo>> taskInfoPage(BizTaskInfoPageParam bizTaskInfoPageParam) {
        return CommonResult.data(bizTaskInfoService.page(bizTaskInfoPageParam));
    }

    /**
     * 添加任务
     *
     * @author ljy
     * @date  2023/12/08 09:03
     */
    @ApiOperationSupport(order = 17)
    @ApiOperation("添加任务")
    @CommonLog("添加任务")
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
    @GetMapping("/biz/taskInfo/detail")
    public CommonResult<BizTaskInfo> taskInfoDetail(@Valid BizTaskInfoIdParam bizTaskInfoIdParam) {
        return CommonResult.data(bizTaskInfoService.detail(bizTaskInfoIdParam));
    }

    @ApiOperationSupport(order = 26)
    @ApiOperation("任务平台-发送消息")
    @GetMapping("/biz/taskGroupRes/sendMsg")
    public CommonResult<BizTaskGroupRes> sendMsg(Map<String,String> map, List<String> receiverIdList) {
        bizTaskInfoService.sendMsg(map,receiverIdList);
        return CommonResult.ok();
    }


    /** 第三方源任务分页*/
    /**
     * 第三方源任务详情分页
     * @author ljy
     * @date  2023/12/18 11:40
     */
    /*@ApiOperationSupport(order = 27)
    @ApiOperation("第三方源任务详情分页")
    @CommonLog("第三方源任务详情分页")
    @GetMapping("/biz/taskInfo/thirdPage")
    public CommonResult<Page<BizTaskInfo>> thirdPage(ThirdTaskPageParam thirdTaskPageParam) {
        return CommonResult.data(bizTaskInfoService.thirdPage(thirdTaskPageParam));
    }*/

    /**
     * 第三方来源添加任务
     * 1.会议 REZ_ID  =  BIZ_TASK_PROJECT 任务来源详情 PRO_SOURCE_ID
     * @author ljy
     * @date  2023/12/18 11:40
     */
    /*@ApiOperationSupport(order = 28)
    @ApiOperation("第三方来源添加任务")
    @CommonLog("第三方来源添加任务")
    @PostMapping("/biz/taskInfo/thirdAdd")
    public CommonResult<String> thirdAdd(@RequestBody @Valid BizTaskInfoAddParam bizTaskInfoAddParam) {
        bizTaskInfoService.thirdAdd(bizTaskInfoAddParam);
        return CommonResult.ok();
    }*/

    /**
     * 修改第三方来源任务
     * @author ljy
     * @date  2023/12/18 11:40
     */
    @ApiOperationSupport(order = 28)
    @ApiOperation("修改第三方来源任务")
    @CommonLog("修改第三方来源任务")
    @PostMapping("/biz/taskInfo/editThirdWork")
    public CommonResult<String> editThirdWorkCore(@RequestBody @Valid ThirdBizTaskInfoParam thirdBizTaskInfoParam) {
        bizTaskInfoService.editThirdWorkCore(thirdBizTaskInfoParam);
        return CommonResult.ok();
    }


    /**
     * 确认任务接口
     * @author ljy
     * @date  2024/03/29 09:40
     */
    @ApiOperationSupport(order = 29)
    @ApiOperation("任务详情 - 确认任务接口")
    @CommonLog("任务详情 - 确认任务接口")
    @GetMapping("/biz/taskInfo/confirm")
    public CommonResult<String> confirmTask(BizTaskInfoIdParam bizTaskInfoIdParam) {
        bizTaskInfoService.confirmTask(bizTaskInfoIdParam);
        return CommonResult.ok();
    }
}
