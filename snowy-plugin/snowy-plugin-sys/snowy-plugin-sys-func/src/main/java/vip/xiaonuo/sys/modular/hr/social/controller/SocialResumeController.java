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
package vip.xiaonuo.sys.modular.hr.social.controller;

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
import vip.xiaonuo.sys.modular.hr.social.entity.SocialResume;
import vip.xiaonuo.sys.modular.hr.social.param.*;
import vip.xiaonuo.sys.modular.hr.social.service.SocialResumeService;
import vip.xiaonuo.sys.modular.hr.social.vo.SocialResumeVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 社招 - （应聘者）简历库信息控制器
 *
 * @author yong.ye
 * @date  2024/07/01 09:35
 */
@Api(tags = "社招 - （应聘者）简历库信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SocialResumeController {

    @Resource
    private SocialResumeService socialResumeService;

    /**
     * 获取社招 - （应聘者）简历库信息分页
     *
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取社招 - （应聘者）简历库信息分页")
//    @SaCheckPermission("/sys/hr/social/resume/page")
    @GetMapping("/sys/hr/social/resume/page")
    public CommonResult<Page<SocialResumeVo>> page(SocialResumePageParam socialResumePageParam) {
        return CommonResult.data(socialResumeService.page(socialResumePageParam));
    }

    /**
     * 添加社招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加社招 - （应聘者）简历库信息")
    @CommonLog("添加社招 - （应聘者）简历库信息")
//    @SaCheckPermission("/sys/hr/social/resume/add")
    @PostMapping("/sys/hr/social/resume/add")
    public CommonResult<String> add(@RequestBody @Valid SocialResumeAddParam socialResumeAddParam) {
        socialResumeService.add(socialResumeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑社招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑社招 - （应聘者）简历库信息")
    @CommonLog("编辑社招 - （应聘者）简历库信息")
//    @SaCheckPermission("/sys/hr/social/resume/edit")
    @PostMapping("/sys/hr/social/resume/edit")
    public CommonResult<String> edit(@RequestBody @Valid SocialResumeEditParam socialResumeEditParam) {
        socialResumeService.edit(socialResumeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除社招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除社招 - （应聘者）简历库信息")
    @CommonLog("删除社招 - （应聘者）简历库信息")
//    @SaCheckPermission("/sys/hr/social/resume/delete")
    @PostMapping("/sys/hr/social/resume/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SocialResumeIdParam> socialResumeIdParamList) {
        socialResumeService.delete(socialResumeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取社招 - （应聘者）简历库信息详情
     *
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取社招 - （应聘者）简历库信息详情")
//    @SaCheckPermission("/sys/hr/social/resume/detail")
    @GetMapping("/sys/hr/social/resume/detail")
    public CommonResult<SocialResume> detail(@Valid SocialResumeIdParam socialResumeIdParam) {
        return CommonResult.data(socialResumeService.detail(socialResumeIdParam));
    }


    /**
     * 添加社招 - （应聘者）简历库信息 - 页面上传新增简历（支持批量 文件名格式 姓名_手机号_应聘岗位.pdf）
     * 支持 - _ + —— 符合分割
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("添加社招 - （应聘者）简历库信息 - 页面上传新增简历（支持批量 文件名格式 姓名_手机号_应聘岗位.pdf）")
    @CommonLog("添加社招 - （应聘者）简历库信息 - 页面上传新增简历（支持批量 文件名格式 姓名_手机号_应聘岗位.pdf）")
//    @SaCheckPermission("/sys/hr/social/resume/save")
    @PostMapping("/sys/hr/social/resume/save")
    public CommonResult<String> save(@RequestBody @Valid UploadParam uploadParam) {
        socialResumeService.save(uploadParam);
        return CommonResult.ok();
    }


    /**
     * 发送 短信 ，应聘者填写个人信息
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("发送 短信 ")
    @CommonLog("发送 短信 ")
//    @SaCheckPermission("/sys/hr/social/resume/send")
    @PostMapping("/sys/hr/social/resume/send")
    public CommonResult<String> send(@RequestBody @Valid SocialResumeIdParam idParam) {
        socialResumeService.send(idParam);
        return CommonResult.ok();
    }


    /**
     * 应聘者填写个人信息，提交
     * @author yong.ye
     * @date  2024/07/01 09:35
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("应聘者填写个人信息，提交")
    @CommonLog("应聘者填写个人信息，提交")
//    @SaCheckPermission("/sys/hr/social/resume/submit")
    @PostMapping("/sys/hr/social/resume/submit")
    public CommonResult<String> submit(@RequestBody @Valid ResumeParam resumeParam) {
        socialResumeService.submit(resumeParam);
        return CommonResult.ok();
    }
}
