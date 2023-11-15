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
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.school.entity.ResumeRecord;
import vip.xiaonuo.biz.modular.school.param.ResumeRecordAddParam;
import vip.xiaonuo.biz.modular.school.param.ResumeRecordEditParam;
import vip.xiaonuo.biz.modular.school.param.ResumeRecordIdParam;
import vip.xiaonuo.biz.modular.school.param.ResumeRecordPageParam;
import vip.xiaonuo.biz.modular.school.service.ResumeRecordService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 校招-简历操作记录控制器
 *
 * @author yong.ye
 * @date  2023/11/15 08:55
 */
@Api(tags = "校招-简历操作记录控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ResumeRecordController {

    @Resource
    private ResumeRecordService resumeRecordService;

    /**
     * 获取校招-简历操作记录分页
     *
     * @author yong.ye
     * @date  2023/11/15 08:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取校招-简历操作记录分页")
    @SaCheckPermission("/biz/school/page")
    @GetMapping("/biz/school/page")
    public CommonResult<Page<ResumeRecord>> page(ResumeRecordPageParam resumeRecordPageParam) {
        return CommonResult.data(resumeRecordService.page(resumeRecordPageParam));
    }

    /**
     * 添加校招-简历操作记录
     *
     * @author yong.ye
     * @date  2023/11/15 08:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加校招-简历操作记录")
    @CommonLog("添加校招-简历操作记录")
    @SaCheckPermission("/biz/school/add")
    @PostMapping("/biz/school/add")
    public CommonResult<String> add(@RequestBody @Valid ResumeRecordAddParam resumeRecordAddParam) {
        resumeRecordService.add(resumeRecordAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑校招-简历操作记录
     *
     * @author yong.ye
     * @date  2023/11/15 08:55
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑校招-简历操作记录")
    @CommonLog("编辑校招-简历操作记录")
    @SaCheckPermission("/biz/school/edit")
    @PostMapping("/biz/school/edit")
    public CommonResult<String> edit(@RequestBody @Valid ResumeRecordEditParam resumeRecordEditParam) {
        resumeRecordService.edit(resumeRecordEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除校招-简历操作记录
     *
     * @author yong.ye
     * @date  2023/11/15 08:55
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除校招-简历操作记录")
    @CommonLog("删除校招-简历操作记录")
    @SaCheckPermission("/biz/school/delete")
    @PostMapping("/biz/school/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ResumeRecordIdParam> resumeRecordIdParamList) {
        resumeRecordService.delete(resumeRecordIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取校招-简历操作记录详情
     *
     * @author yong.ye
     * @date  2023/11/15 08:55
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取校招-简历操作记录详情")
    @SaCheckPermission("/biz/school/detail")
    @GetMapping("/biz/school/detail")
    public CommonResult<ResumeRecord> detail(@Valid ResumeRecordIdParam resumeRecordIdParam) {
        return CommonResult.data(resumeRecordService.detail(resumeRecordIdParam));
    }
}
