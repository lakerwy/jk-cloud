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
package vip.xiaonuo.biz.modular.school.controller;

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
import vip.xiaonuo.biz.modular.school.param.*;
import vip.xiaonuo.biz.modular.school.vo.ResumeVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.school.entity.Resume;
import vip.xiaonuo.biz.modular.school.service.ResumeService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 校招-简历控制器
 *
 * @author yong.ye
 * @date  2023/11/15 08:54
 */
@Api(tags = "校招-简历控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ResumeController {

    @Resource
    private ResumeService resumeService;

    /**
     * 获取校招-简历分页
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取校招-简历分页")
    @SaCheckPermission("/biz/sys/resume/getResumePageList")
    @GetMapping("/biz/sys/resume/getResumePageList")
    public CommonResult<Page<Resume>> page(ResumePageParam resumePageParam) {
        return CommonResult.data(resumeService.page(resumePageParam));
    }

    /**
     * 添加校招-简历
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加校招-简历")
    @CommonLog("添加校招-简历")
    @SaCheckPermission("/biz/sys/resume/add")
    @PostMapping("/biz/sys/resume/add")
    public CommonResult<String> add(@RequestBody @Valid ResumeAddParam resumeAddParam) {
        resumeService.add(resumeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑校招-简历
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑校招-简历")
    @CommonLog("编辑校招-简历")
    @SaCheckPermission("/biz/sys/resume/edit")
    @PostMapping("/biz/sys/resume/edit")
    public CommonResult<String> edit(@RequestBody @Valid ResumeEditParam resumeEditParam) {
        resumeService.edit(resumeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除校招-简历
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除校招-简历")
    @CommonLog("删除校招-简历")
    @SaCheckPermission("/biz/sys/resume/delete")
    @PostMapping("/biz/sys/resume/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ResumeIdParam> resumeIdParamList) {
        resumeService.delete(resumeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取校招-简历详情
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取校招-简历详情")
    @SaCheckPermission("/biz/sys/resume/getResumeDetail")
    @GetMapping("/biz/sys/resume/getResumeDetail")
    public CommonResult<ResumeVo> detail(@Valid ResumeIdParam resumeIdParam) {
        return CommonResult.data(resumeService.detail(resumeIdParam));
    }

    /**
     * 简历操作 - 针对 HR筛选|部门筛选|面试通知|面试登记|Offer登记
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("简历操作 - 针对 HR筛选|部门筛选|面试通知|面试登记|Offer登记")
    @CommonLog("简历操作 - 针对 HR筛选|部门筛选|面试通知|面试登记|Offer登记")
    @SaCheckPermission("/biz/sys/resume/doResumeAction")
    @PostMapping("/biz/sys/resume/doResumeAction")
    public CommonResult<String> doResumeAction(@RequestBody @Valid ResumeRecordAddParam resumeRecordAddParam) {
        resumeService.doResumeAction(resumeRecordAddParam);
        return CommonResult.ok();
    }

    /**
     * 批量简历操作 - 针对 HR筛选|部门筛选|面试通知|面试登记|Offer登记
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("批量简历操作 - 针对 HR筛选|部门筛选|面试通知|面试登记|Offer登记")
    @CommonLog("批量简历操作 - 针对 HR筛选|部门筛选|面试通知|面试登记|Offer登记")
    @SaCheckPermission("/biz/sys/resume/doResumeActionBatch")
    @PostMapping("/biz/sys/resume/doResumeActionBatch")
    public CommonResult<String> doResumeActionBatch(@RequestBody @Valid BatchResumeRecord batchResumeRecord) {
        resumeService.doResumeActionBatch(batchResumeRecord);
        return CommonResult.ok();
    }

    /**
     * 投递简历 - 保存简历
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("投递简历 - 保存简历")
    @CommonLog("投递简历 - 保存简历")
    @SaCheckPermission("/biz/sys/resume/saveResume")
    @PostMapping("/biz/sys/resume/saveResume")
    public CommonResult<String> saveResume(@RequestBody @Valid ResumeAddParam resumeAddParam) {
        resumeService.saveResume(resumeAddParam);
        return CommonResult.ok();
    }

    /**
     * 简历部门推荐 【批量操作部门推荐】
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("简历部门推荐 【批量操作部门推荐】")
    @CommonLog("简历部门推荐 【批量操作部门推荐】")
    @SaCheckPermission("/biz/sys/resume/assignDepts")
    @PostMapping("/biz/sys/resume/assignDepts")
    public CommonResult<String> assignDept(@RequestBody @Valid AssignDeptsDto assignDept) {
        resumeService.assignDept(assignDept);
        return CommonResult.ok();
    }

    /**
     * 根据员工信息，获取所属部门，再获取该员工可以查阅的部门推荐简历
     *
     * @author yong.ye
     * @date  2023/11/15 08:54
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("根据员工信息，获取所属部门，再获取该员工可以查阅的部门推荐简历")
    @SaCheckPermission("/biz/sys/resume/getResumesByUserId")
    @GetMapping("/biz/sys/resume/getResumesByUserId")
    public CommonResult<Page<Resume>> pageByUserId(ResumePageParam resumePageParam) {
        return CommonResult.data(resumeService.pageByUserId(resumePageParam));
    }

//    /**
//     * 简历-取消部门推荐  【批量操作取消部门推荐】
//     *
//     * @author yong.ye
//     * @date  2023/11/15 08:54
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("简历-取消部门推荐  【批量操作取消部门推荐】")
//    @CommonLog("简历-取消部门推荐  【批量操作取消部门推荐】")
//    @SaCheckPermission("/biz/sys/resume/cancelAssignDepts")
//    @PostMapping("/biz/sys/resume/cancelAssignDepts")
//    public CommonResult<String> cancelAssignDept(@RequestBody @Valid AssignDeptsDto assignDept) {
//        resumeService.cancelAssignDept(assignDept);
//        return CommonResult.ok();
//    }
}
