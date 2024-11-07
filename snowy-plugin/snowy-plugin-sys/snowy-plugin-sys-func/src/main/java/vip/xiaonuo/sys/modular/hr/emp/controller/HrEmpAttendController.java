package vip.xiaonuo.sys.modular.hr.emp.controller;

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
import vip.xiaonuo.sys.modular.hr.emp.entity.HrEmpAttend;
import vip.xiaonuo.sys.modular.hr.emp.param.*;
import vip.xiaonuo.sys.modular.hr.emp.service.HrEmpAttendService;
import vip.xiaonuo.sys.modular.hr.emp.vo.ImportResult;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 员工月度考勤表 - 单独 - 非工资系统控制器 【独立于工资系统：不启用工资系统时，可以单独使用的考勤记录，启用工资系统时，遵循工资系统考勤模块】
 *
 * @author yong.ye
 * @date  2024/11/06 16:53
 */
@Api(tags = "员工月度考勤表 - 单独 - 非工资系统控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrEmpAttendController {

    @Resource
    private HrEmpAttendService hrEmpAttendService;

    /**
     * 获取员工月度考勤表 - 单独 - 非工资系统分页
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取员工月度考勤表 - 单独 - 非工资系统分页")
//    @SaCheckPermission("/sys/hr/emp/attend/page")
    @GetMapping("/sys/hr/emp/attend/page")
    public CommonResult<Page<HrEmpAttend>> page(HrEmpAttendPageParam hrEmpAttendPageParam) {
        return CommonResult.data(hrEmpAttendService.page(hrEmpAttendPageParam));
    }

    /**
     * 添加员工月度考勤表 - 单独 - 非工资系统
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加员工月度考勤表 - 单独 - 非工资系统")
    @CommonLog("添加员工月度考勤表 - 单独 - 非工资系统")
//    @SaCheckPermission("/sys/hr/emp/attend/add")
    @PostMapping("/sys/hr/emp/attend/add")
    public CommonResult<String> add(@RequestBody @Valid HrEmpAttendAddParam hrEmpAttendAddParam) {
        hrEmpAttendService.add(hrEmpAttendAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑员工月度考勤表 - 单独 - 非工资系统
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑员工月度考勤表 - 单独 - 非工资系统")
    @CommonLog("编辑员工月度考勤表 - 单独 - 非工资系统")
//    @SaCheckPermission("/sys/hr/emp/attend/edit")
    @PostMapping("/sys/hr/emp/attend/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrEmpAttendEditParam hrEmpAttendEditParam) {
        hrEmpAttendService.edit(hrEmpAttendEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除员工月度考勤表 - 单独 - 非工资系统
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除员工月度考勤表 - 单独 - 非工资系统")
    @CommonLog("删除员工月度考勤表 - 单独 - 非工资系统")
//    @SaCheckPermission("/sys/hr/emp/attend/delete")
    @PostMapping("/sys/hr/emp/attend/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrEmpAttendIdParam> hrEmpAttendIdParamList) {
        hrEmpAttendService.delete(hrEmpAttendIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取员工月度考勤表 - 单独 - 非工资系统详情
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取员工月度考勤表 - 单独 - 非工资系统详情")
//    @SaCheckPermission("/sys/hr/emp/attend/detail")
    @GetMapping("/sys/hr/emp/attend/detail")
    public CommonResult<HrEmpAttend> detail(@Valid HrEmpAttendIdParam hrEmpAttendIdParam) {
        return CommonResult.data(hrEmpAttendService.detail(hrEmpAttendIdParam));
    }


    /**
     * 导入员工月度考勤 - 校验逻辑
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("导入员工月度考勤 - 校验逻辑")
    @CommonLog("导入员工月度考勤 - 校验逻辑")
    @PostMapping("/sys/hr/emp/attend/importCheck")
    public CommonResult<ImportResult> importCheck(@RequestBody @Valid ImportParam importParam) {
        return CommonResult.data(hrEmpAttendService.importCheck(importParam));
    }


    /**
     * 导入员工月度考勤 - 确认导入
     *
     * @author yong.ye
     * @date  2024/11/06 16:53
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("导入员工月度考勤 - 确认导入")
    @CommonLog("导入员工月度考勤 - 确认导入")
    @PostMapping("/sys/hr/emp/attend/doImport")
    public CommonResult<Boolean> doImport(@RequestBody @Valid ImportParam importParam) {
        return CommonResult.data(hrEmpAttendService.doImport(importParam));
    }

}
