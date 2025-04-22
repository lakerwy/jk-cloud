package vip.xiaonuo.sys.modular.hr.icManage.controller;

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
import vip.xiaonuo.sys.modular.hr.icManage.entity.IcManageApply;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyAddParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyEditParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyIdParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageApplyPageParam;
import vip.xiaonuo.sys.modular.hr.icManage.service.IcManageApplyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 工商信息新增|变更|注销--流程申请控制器
 *
 * @author yy
 * @date  2025/04/21 15:58
 */
@Api(tags = "工商信息新增|变更|注销--流程申请控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class IcManageApplyController {

    @Resource
    private IcManageApplyService icManageApplyService;

    /**
     * 获取工商信息新增|变更|注销--流程申请分页
     *
     * @author yy
     * @date  2025/04/21 15:58
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取工商信息新增|变更|注销--流程申请分页")
//    @SaCheckPermission("/sys/icManage/apply/page")
    @GetMapping("/sys/icManage/apply/page")
    public CommonResult<Page<IcManageApply>> page(IcManageApplyPageParam icManageApplyPageParam) {
        return CommonResult.data(icManageApplyService.page(icManageApplyPageParam));
    }

    /**
     * 添加工商信息新增|变更|注销--流程申请
     *
     * @author yy
     * @date  2025/04/21 15:58
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加工商信息新增|变更|注销--流程申请")
    @CommonLog("添加工商信息新增|变更|注销--流程申请")
//    @SaCheckPermission("/sys/icManage/apply/add")
    @PostMapping("/sys/icManage/apply/add")
    public CommonResult<String> add(@RequestBody @Valid IcManageApplyAddParam icManageApplyAddParam) {
        icManageApplyService.add(icManageApplyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑工商信息新增|变更|注销--流程申请
     *
     * @author yy
     * @date  2025/04/21 15:58
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑工商信息新增|变更|注销--流程申请")
    @CommonLog("编辑工商信息新增|变更|注销--流程申请")
//    @SaCheckPermission("/sys/icManage/apply/edit")
    @PostMapping("/sys/icManage/apply/edit")
    public CommonResult<String> edit(@RequestBody @Valid IcManageApplyEditParam icManageApplyEditParam) {
        icManageApplyService.edit(icManageApplyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除工商信息新增|变更|注销--流程申请
     *
     * @author yy
     * @date  2025/04/21 15:58
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除工商信息新增|变更|注销--流程申请")
    @CommonLog("删除工商信息新增|变更|注销--流程申请")
//    @SaCheckPermission("/sys/icManage/apply/delete")
    @PostMapping("/sys/icManage/apply/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<IcManageApplyIdParam> icManageApplyIdParamList) {
        icManageApplyService.delete(icManageApplyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工商信息新增|变更|注销--流程申请详情
     *
     * @author yy
     * @date  2025/04/21 15:58
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取工商信息新增|变更|注销--流程申请详情")
//    @SaCheckPermission("/sys/icManage/apply/detail")
    @GetMapping("/sys/icManage/apply/detail")
    public CommonResult<IcManageApply> detail(@Valid IcManageApplyIdParam icManageApplyIdParam) {
        return CommonResult.data(icManageApplyService.detail(icManageApplyIdParam));
    }
}
