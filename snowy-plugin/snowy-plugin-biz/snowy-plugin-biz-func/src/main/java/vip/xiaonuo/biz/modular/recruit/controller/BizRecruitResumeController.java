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
package vip.xiaonuo.biz.modular.recruit.controller;

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
import vip.xiaonuo.biz.modular.recruit.param.dto.ResumeAddDto;
import vip.xiaonuo.biz.modular.recruit.param.dto.ResumeParserDto;
import vip.xiaonuo.biz.modular.recruit.param.dto.ResumeRegistSaveDto;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitResume;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitResumeAddParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitResumeEditParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitResumeIdParam;
import vip.xiaonuo.biz.modular.recruit.param.BizRecruitResumePageParam;
import vip.xiaonuo.biz.modular.recruit.service.BizRecruitResumeService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-招聘-简历库控制器
 *
 * @author yong.ye
 * @date  2023/08/23 17:08
 */
@Api(tags = "业务表-招聘-简历库控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizRecruitResumeController {

    @Resource
    private BizRecruitResumeService bizRecruitResumeService;

    /**
     * 获取业务表-招聘-简历库分页
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-招聘-简历库分页")
    @GetMapping("/biz/recruit/resume/page")
    public CommonResult<Page<BizRecruitResume>> page(BizRecruitResumePageParam bizRecruitResumePageParam) {
        return CommonResult.data(bizRecruitResumeService.page(bizRecruitResumePageParam));
    }

    /**
     * 添加业务表-招聘-简历库
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-招聘-简历库")
    @CommonLog("添加业务表-招聘-简历库")
    @PostMapping("/biz/recruit/resume/add")
    public CommonResult<String> add(@RequestBody @Valid BizRecruitResumeAddParam bizRecruitResumeAddParam) {
        bizRecruitResumeService.add(bizRecruitResumeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-招聘-简历库
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-招聘-简历库")
    @CommonLog("编辑业务表-招聘-简历库")
    @PostMapping("/biz/recruit/resume/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizRecruitResumeEditParam bizRecruitResumeEditParam) {
        bizRecruitResumeService.edit(bizRecruitResumeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-招聘-简历库
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-招聘-简历库")
    @CommonLog("删除业务表-招聘-简历库")
    @PostMapping("/biz/recruit/resume/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizRecruitResumeIdParam> bizRecruitResumeIdParamList) {
        bizRecruitResumeService.delete(bizRecruitResumeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-招聘-简历库详情
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-招聘-简历库详情")
    @GetMapping("/biz/recruit/resume/detail")
    public CommonResult<BizRecruitResume> detail(@Valid BizRecruitResumeIdParam bizRecruitResumeIdParam) {
        return CommonResult.data(bizRecruitResumeService.detail(bizRecruitResumeIdParam));
    }

    /**
     * 添加业务表-招聘-简历库(上传附件，简历自动解析)
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("添加业务表-招聘-简历库(上传附件，简历自动解析)")
    @CommonLog("添加业务表-招聘-简历库(上传附件，简历自动解析)")
    @PostMapping("/biz/recruit/resume/addAuto")
    public CommonResult<String> addAuto(@RequestBody @Valid ResumeAddDto resumeAddDto) {
        bizRecruitResumeService.addAuto(resumeAddDto);
        return CommonResult.ok();
    }


    /**
     * 业务表-招聘-简历库(解析失败-重新解析)
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("业务表-招聘-简历库(解析失败-重新解析)")
    @CommonLog("业务表-招聘-简历库(解析失败-重新解析)")
    @PostMapping("/biz/recruit/resume/resumeParser")
    public CommonResult<String> resumeParser(@RequestBody @Valid ResumeParserDto resumeParserDto) {
        bizRecruitResumeService.resumeParser(resumeParserDto);
        return CommonResult.ok();
    }

    /**
     * 业务表-招聘-简历库(应聘登记表提交保存)
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("业务表-招聘-简历库(应聘登记表提交保存)")
    @CommonLog("业务表-招聘-简历库(应聘登记表提交保存)")
    @PostMapping("/biz/recruit/resume/registerSave")
    public CommonResult<String> registerSave(@RequestBody @Valid ResumeRegistSaveDto resumeRegistSaveDto) {
        bizRecruitResumeService.registerSave(resumeRegistSaveDto);
        return CommonResult.ok();
    }


    /**
     * 根据简历Id,获取业务表-招聘-简历库 - 应聘登记表信息
     *
     * @author yong.ye
     * @date  2023/08/23 17:08
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取业务表-招聘-简历库 - 应聘登记表信息")
    @GetMapping("/biz/recruit/resume/registerDetail")
    public CommonResult<String> registerDetail(@Valid BizRecruitResumeIdParam bizRecruitResumeIdParam) {
        return CommonResult.data(bizRecruitResumeService.registerDetail(bizRecruitResumeIdParam));
    }
}
