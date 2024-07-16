package vip.xiaonuo.oa.modular.attendance.apply.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.oa.modular.attendance.apply.entity.Apply;
import vip.xiaonuo.oa.modular.attendance.apply.entity.ApplyJson;
import vip.xiaonuo.oa.modular.attendance.apply.entity.HolidayJson;
import vip.xiaonuo.oa.modular.attendance.apply.enums.ApplyEnum;
import vip.xiaonuo.oa.modular.attendance.apply.param.*;
import vip.xiaonuo.oa.modular.attendance.apply.service.ApplyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 考勤申请管理表控制器
 *
 * @author mayike
 * @date  2024/07/15 09:56
 */
@Api(tags = "考勤申请管理表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ApplyController {

    @Resource
    @Qualifier("applyServiceImpl")
    private ApplyService applyService;

    /**
     * 获取考勤申请管理表分页
     *
     * @author mayike
     * @date  2024/07/15 09:56
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取考勤申请管理表分页")
//    @SaCheckPermission("/oa/apply/page")
    @GetMapping("/oa/apply/page")
    public CommonResult<Page<Apply<ApplyJson>>> page(ApplyPageParam applyPageParam) {
        return CommonResult.data(applyService.page(applyPageParam, ApplyJson.class));
    }

    /**
     * 添加考勤申请管理表
     *
     * @author mayike
     * @date  2024/07/15 09:56
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加考勤申请管理表")
    @CommonLog("添加考勤申请管理表")
//    @SaCheckPermission("/oa/apply/add")
    @PostMapping("/oa/apply/add")
    public CommonResult<String> add(@RequestBody @Valid ApplyAddParam applyAddParam) {
        applyService.add(applyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤申请管理表
     *
     * @author mayike
     * @date  2024/07/15 09:56
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑考勤申请管理表")
    @CommonLog("编辑考勤申请管理表")
//    @SaCheckPermission("/oa/apply/edit")
    @PostMapping("/oa/apply/edit")
    public CommonResult<String> edit(@RequestBody @Valid ApplyEditParam applyEditParam) {
        applyService.edit(applyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除考勤申请管理表
     *
     * @author mayike
     * @date  2024/07/15 09:56
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除考勤申请管理表")
    @CommonLog("删除考勤申请管理表")
//    @SaCheckPermission("/oa/apply/delete")
    @PostMapping("/oa/apply/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ApplyIdParam> applyIdParamList) {
        applyService.delete(applyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取考勤申请管理表详情
     *
     * @author mayike
     * @date  2024/07/15 09:56
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取考勤申请管理表详情")
//    @SaCheckPermission("/oa/apply/detail")
    @GetMapping("/oa/apply/detail")
    public CommonResult<Apply> detail(@Valid ApplyIdParam applyIdParam) {
        return CommonResult.data(applyService.detail(applyIdParam, ApplyJson.class));
    }


    @ApiOperationSupport(order = 11)
    @ApiOperation("获取请假申请分页")
//    @SaCheckPermission("/oa/apply/page")
    @GetMapping("/oa/apply/holidayPage")
    public CommonResult<Page<Apply<HolidayJson>>> holidayPage(ApplyPageParam applyPageParam) {
        applyPageParam.setApplyType(ApplyEnum.HOLIDAY_APPLY.getType());
        return CommonResult.data(applyService.page(applyPageParam, HolidayJson.class));
    }

    @ApiOperationSupport(order = 12)
    @ApiOperation("添加请假申请管理表")
    @CommonLog("添加请假申请管理表")
//    @SaCheckPermission("/oa/apply/add")
    @PostMapping("/oa/apply/holidayAdd")
    public CommonResult<String> holidayAdd(@RequestBody @Valid HolidayApplyAddParam applyAddParam) {
        applyService.add(applyAddParam);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 13)
    @ApiOperation("编辑请假申请")
    @CommonLog("编辑请假申请")
//    @SaCheckPermission("/oa/apply/add")
    @PostMapping("/oa/apply/holidayEdit")
    public CommonResult<String> holidayEdit(@RequestBody @Valid HolidayApplyEditParam applyEditParam) {
        applyService.edit(applyEditParam);
        return CommonResult.ok();
    }

    /**
     * 获取考勤申请管理表详情
     *
     * @author mayike
     * @date  2024/07/15 09:56
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("获取考勤申请管理表详情")
//    @SaCheckPermission("/oa/apply/detail")
    @GetMapping("/oa/apply/holidayDetail")
    public CommonResult<Apply> holidayDetail(@Valid ApplyIdParam applyIdParam) {
        return CommonResult.data(applyService.detail(applyIdParam, HolidayJson.class));
    }
}
