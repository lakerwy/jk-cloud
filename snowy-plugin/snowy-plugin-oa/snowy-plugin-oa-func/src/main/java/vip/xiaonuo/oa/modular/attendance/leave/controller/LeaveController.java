package vip.xiaonuo.oa.modular.attendance.leave.controller;

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
import vip.xiaonuo.oa.modular.attendance.leave.entity.Leave;
import vip.xiaonuo.oa.modular.attendance.leave.param.*;
import vip.xiaonuo.oa.modular.attendance.leave.service.LeaveService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 请假记录表控制器
 *
 * @author mayike
 * @date  2024/06/24 16:55
 */
@Api(tags = "请假记录表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class LeaveController {

    @Resource
    private LeaveService leaveService;

    /**
     * 获取请假记录表分页
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取请假记录表分页")
//    @SaCheckPermission("/oa/leave/page")
    @GetMapping("/oa/leave/page")
    public CommonResult<Page<Leave>> page(LeavePageParam leavePageParam) {
        return CommonResult.data(leaveService.page(leavePageParam));
    }

    /**
     * 添加请假记录表
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加请假记录表")
    @CommonLog("添加请假记录表")
//    @SaCheckPermission("/oa/leave/add")
    @PostMapping("/oa/leave/add")
    public CommonResult<String> add(@RequestBody @Valid LeaveAddParam leaveAddParam) {
        leaveService.add(leaveAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑请假记录表
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑请假记录表")
    @CommonLog("编辑请假记录表")
//    @SaCheckPermission("/oa/leave/edit")
    @PostMapping("/oa/leave/edit")
    public CommonResult<String> edit(@RequestBody @Valid LeaveEditParam leaveEditParam) {
        leaveService.edit(leaveEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除请假记录表
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除请假记录表")
    @CommonLog("删除请假记录表")
//    @SaCheckPermission("/oa/leave/delete")
    @PostMapping("/oa/leave/delete")
    public CommonResult<String> deleteLeave(@RequestBody @Valid @NotEmpty(message = "集合不能为空") CommonValidList<LeaveIdParam> leaveIdParamList) {
        leaveService.delete(leaveIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取请假记录表详情
     *
     * @author mayike
     * @date  2024/06/24 16:55
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取请假记录表详情")
//    @SaCheckPermission("/oa/leave/detail")
    @GetMapping("/oa/leave/detail")
    public CommonResult<Leave> detail(@Valid LeaveIdParam leaveIdParam) {
        return CommonResult.data(leaveService.detail(leaveIdParam));
    }
}
