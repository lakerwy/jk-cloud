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
package vip.xiaonuo.biz.modular.teambition.taskInfoComment.controller;

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
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.entity.BizTaskComment;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentAddParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentEditParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentIdParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentPageParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.service.BizTaskCommentService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 任务管理平台-任务评论控制器
 *
 * @author ljy
 * @date  2023/12/13 17:51
 */
@Api(tags = "任务管理平台-任务评论控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizTaskCommentController {

    @Resource
    private BizTaskCommentService bizTaskCommentService;

    /**
     * 获取任务管理平台-任务评论分页
     *
     * @author ljy
     * @date  2023/12/13 17:51
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取任务管理平台-任务评论分页")
    @GetMapping("/biz/taskComment/page")
    public CommonResult<Page<BizTaskComment>> page(BizTaskCommentPageParam bizTaskCommentPageParam) {
        return CommonResult.data(bizTaskCommentService.page(bizTaskCommentPageParam));
    }

    /**
     * 添加任务管理平台-任务评论
     *
     * @author ljy
     * @date  2023/12/13 17:51
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加任务管理平台-任务评论")
    @CommonLog("添加任务管理平台-任务评论")
    @PostMapping("/biz/taskComment/add")
    public CommonResult<String> add(@RequestBody @Valid BizTaskCommentAddParam bizTaskCommentAddParam) {
        bizTaskCommentService.add(bizTaskCommentAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑任务管理平台-任务评论
     *
     * @author ljy
     * @date  2023/12/13 17:51
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑任务管理平台-任务评论")
    @CommonLog("编辑任务管理平台-任务评论")
    @PostMapping("/biz/taskComment/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizTaskCommentEditParam bizTaskCommentEditParam) {
        bizTaskCommentService.edit(bizTaskCommentEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除任务管理平台-任务评论
     *
     * @author ljy
     * @date  2023/12/13 17:51
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除任务管理平台-任务评论")
    @CommonLog("删除任务管理平台-任务评论")
    @PostMapping("/biz/taskComment/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizTaskCommentIdParam> bizTaskCommentIdParamList) {
        bizTaskCommentService.delete(bizTaskCommentIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取任务管理平台-任务评论详情
     *
     * @author ljy
     * @date  2023/12/13 17:51
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取任务管理平台-任务评论详情")
    @GetMapping("/biz/taskComment/detail")
    public CommonResult<BizTaskComment> detail(@Valid BizTaskCommentIdParam bizTaskCommentIdParam) {
        return CommonResult.data(bizTaskCommentService.detail(bizTaskCommentIdParam));
    }
}
