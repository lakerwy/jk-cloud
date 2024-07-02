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
import vip.xiaonuo.sys.modular.hr.social.entity.SocialInterview;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewAddParam;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewEditParam;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewIdParam;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewPageParam;
import vip.xiaonuo.sys.modular.hr.social.service.SocialInterviewService;
import vip.xiaonuo.sys.modular.hr.social.vo.SocialInterviewVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 社招 - （应聘者）- 面试入职流程控制器
 *
 * @author yong.ye
 * @date  2024/07/01 09:36
 */
@Api(tags = "社招 - （应聘者）- 面试入职流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SocialInterviewController {

    @Resource
    private SocialInterviewService socialInterviewService;

    /**
     * 获取社招 - （应聘者）- 面试入职流程分页
     *
     * @author yong.ye
     * @date  2024/07/01 09:36
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取社招 - （应聘者）- 面试入职流程分页")
//    @SaCheckPermission("/sys/hr/social/interview/page")
    @GetMapping("/sys/hr/social/interview/page")
    public CommonResult<Page<SocialInterviewVo>> page(SocialInterviewPageParam socialInterviewPageParam) {
        return CommonResult.data(socialInterviewService.page(socialInterviewPageParam));
    }

    /**
     * 添加社招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/07/01 09:36
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加社招 - （应聘者）- 面试入职流程")
    @CommonLog("添加社招 - （应聘者）- 面试入职流程")
//    @SaCheckPermission("/sys/hr/social/interview/add")
    @PostMapping("/sys/hr/social/interview/add")
    public CommonResult<String> add(@RequestBody @Valid SocialInterviewAddParam socialInterviewAddParam) {
        socialInterviewService.add(socialInterviewAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑社招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/07/01 09:36
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑社招 - （应聘者）- 面试入职流程")
    @CommonLog("编辑社招 - （应聘者）- 面试入职流程")
//    @SaCheckPermission("/sys/hr/social/interview/edit")
    @PostMapping("/sys/hr/social/interview/edit")
    public CommonResult<String> edit(@RequestBody @Valid SocialInterviewEditParam socialInterviewEditParam) {
        socialInterviewService.edit(socialInterviewEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除社招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/07/01 09:36
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除社招 - （应聘者）- 面试入职流程")
    @CommonLog("删除社招 - （应聘者）- 面试入职流程")
//    @SaCheckPermission("/sys/hr/social/interview/delete")
    @PostMapping("/sys/hr/social/interview/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SocialInterviewIdParam> socialInterviewIdParamList) {
        socialInterviewService.delete(socialInterviewIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取社招 - （应聘者）- 面试入职流程详情
     *
     * @author yong.ye
     * @date  2024/07/01 09:36
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取社招 - （应聘者）- 面试入职流程详情")
//    @SaCheckPermission("/sys/hr/social/interview/detail")
    @GetMapping("/sys/hr/social/interview/detail")
    public CommonResult<SocialInterviewVo> detail(@Valid SocialInterviewIdParam socialInterviewIdParam) {
        return CommonResult.data(socialInterviewService.detail(socialInterviewIdParam));
    }
}
