package vip.xiaonuo.biz.modular.prj.expense.controller;

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
import vip.xiaonuo.biz.modular.prj.expense.entity.BizDebitApply;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyAddParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyEditParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyIdParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizDebitApplyPageParam;
import vip.xiaonuo.biz.modular.prj.expense.service.BizDebitApplyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务管理 - 借支单 - 申请流程控制器
 *
 * @author yong.ye
 * @date  2024/07/30 15:33
 */
@Api(tags = "财务管理 - 借支单 - 申请流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizDebitApplyController {

    @Resource
    private BizDebitApplyService bizDebitApplyService;

    /**
     * 获取财务管理 - 借支单 - 申请流程分页
     *
     * @author yong.ye
     * @date  2024/07/30 15:33
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务管理 - 借支单 - 申请流程分页")
//    @SaCheckPermission("/biz/prj/debit/page")
    @GetMapping("/biz/prj/debit/page")
    public CommonResult<Page<BizDebitApply>> page(BizDebitApplyPageParam bizDebitApplyPageParam) {
        return CommonResult.data(bizDebitApplyService.page(bizDebitApplyPageParam));
    }

    /**
     * 添加财务管理 - 借支单 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:33
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务管理 - 借支单 - 申请流程")
    @CommonLog("添加财务管理 - 借支单 - 申请流程")
//    @SaCheckPermission("/biz/prj/debit/add")
    @PostMapping("/biz/prj/debit/add")
    public CommonResult<String> add(@RequestBody @Valid BizDebitApplyAddParam bizDebitApplyAddParam) {
        bizDebitApplyService.add(bizDebitApplyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务管理 - 借支单 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:33
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务管理 - 借支单 - 申请流程")
    @CommonLog("编辑财务管理 - 借支单 - 申请流程")
//    @SaCheckPermission("/biz/prj/debit/edit")
    @PostMapping("/biz/prj/debit/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizDebitApplyEditParam bizDebitApplyEditParam) {
        bizDebitApplyService.edit(bizDebitApplyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务管理 - 借支单 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:33
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务管理 - 借支单 - 申请流程")
    @CommonLog("删除财务管理 - 借支单 - 申请流程")
//    @SaCheckPermission("/biz/prj/debit/delete")
    @PostMapping("/biz/prj/debit/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizDebitApplyIdParam> bizDebitApplyIdParamList) {
        bizDebitApplyService.delete(bizDebitApplyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务管理 - 借支单 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/07/30 15:33
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务管理 - 借支单 - 申请流程详情")
//    @SaCheckPermission("/biz/prj/debit/detail")
    @GetMapping("/biz/prj/debit/detail")
    public CommonResult<BizDebitApply> detail(@Valid BizDebitApplyIdParam bizDebitApplyIdParam) {
        return CommonResult.data(bizDebitApplyService.detail(bizDebitApplyIdParam));
    }
}
