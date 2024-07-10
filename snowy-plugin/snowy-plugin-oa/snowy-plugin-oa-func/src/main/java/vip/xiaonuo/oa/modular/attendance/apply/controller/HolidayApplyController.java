package vip.xiaonuo.oa.modular.attendance.apply.controller;

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
import vip.xiaonuo.oa.modular.attendance.apply.entity.HolidayApply;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyAddParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyEditParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyIdParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.HolidayApplyPageParam;
import vip.xiaonuo.oa.modular.attendance.apply.service.HolidayApplyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 请假管理表控制器
 *
 * @author mayike
 * @date  2024/07/09 12:00
 */
@Api(tags = "请假管理表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HolidayApplyController {

    @Resource
    private HolidayApplyService holidayApplyService;

    /**
     * 获取请假管理表分页
     *
     * @author mayike
     * @date  2024/07/09 12:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取请假管理表分页")
    @SaCheckPermission("/oa/apply/page")
    @GetMapping("/oa/apply/page")
    public CommonResult<Page<HolidayApply>> page(HolidayApplyPageParam holidayApplyPageParam) {
        return CommonResult.data(holidayApplyService.page(holidayApplyPageParam));
    }

    /**
     * 请假申请
     * 1、 获取假期类型和假期余额以及规则
     *
     * @author mayike
     * @date  2024/07/09 12:00
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("发起请假申请")
    @CommonLog("发起请假申请")
    @SaCheckPermission("/oa/apply/")
    @PostMapping("/oa/apply/add")
    public CommonResult<String> add(@RequestBody @Valid HolidayApplyAddParam holidayApplyAddParam) {
        holidayApplyService.add(holidayApplyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑请假管理表
     *
     * @author mayike
     * @date  2024/07/09 12:00
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑请假管理表")
    @CommonLog("编辑请假管理表")
    @SaCheckPermission("/oa/apply/edit")
    @PostMapping("/oa/apply/edit")
    public CommonResult<String> edit(@RequestBody @Valid HolidayApplyEditParam holidayApplyEditParam) {
        holidayApplyService.edit(holidayApplyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除请假管理表
     *
     * @author mayike
     * @date  2024/07/09 12:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除请假管理表")
    @CommonLog("删除请假管理表")
    @SaCheckPermission("/oa/apply/delete")
    @PostMapping("/oa/apply/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HolidayApplyIdParam> holidayApplyIdParamList) {
        holidayApplyService.delete(holidayApplyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取请假管理表详情
     *
     * @author mayike
     * @date  2024/07/09 12:00
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取请假管理表详情")
    @SaCheckPermission("/oa/apply/detail")
    @GetMapping("/oa/apply/detail")
    public CommonResult<HolidayApply> detail(@Valid HolidayApplyIdParam holidayApplyIdParam) {
        return CommonResult.data(holidayApplyService.detail(holidayApplyIdParam));
    }
}
