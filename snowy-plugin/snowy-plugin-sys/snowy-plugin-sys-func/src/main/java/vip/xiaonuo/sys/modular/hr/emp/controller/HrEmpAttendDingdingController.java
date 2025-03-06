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
import vip.xiaonuo.sys.modular.hr.emp.entity.HrEmpAttendDingding;
import vip.xiaonuo.sys.modular.hr.emp.param.*;
import vip.xiaonuo.sys.modular.hr.emp.service.HrEmpAttendDingdingService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 员工月度考勤(钉钉)控制器
 *
 * @author pzh
 * @date  2025/02/12 15:17
 */
@Api(tags = "员工月度考勤(钉钉)控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrEmpAttendDingdingController {

    @Resource
    private HrEmpAttendDingdingService hrEmpAttendDingdingService;

    /**
     * 获取员工月度考勤(钉钉)分页
     *
     * @author pzh
     * @date  2025/02/12 15:17
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取员工月度考勤(钉钉)分页")
//    @SaCheckPermission("/sys/emp/page")
    @GetMapping("/sys/emp/page")
    public CommonResult<Page<HrEmpAttendDingding>> page(HrEmpAttendDingdingPageParam hrEmpAttendDingdingPageParam) {
        return CommonResult.data(hrEmpAttendDingdingService.page(hrEmpAttendDingdingPageParam));
    }

    /**
     * 添加员工月度考勤(钉钉)
     *
     * @author pzh
     * @date  2025/02/12 15:17
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加员工月度考勤(钉钉)")
    @CommonLog("添加员工月度考勤(钉钉)")
//    @SaCheckPermission("/sys/emp/add")
    @PostMapping("/sys/emp/add")
    public CommonResult<String> add(@RequestBody @Valid HrEmpAttendDingdingAddParam hrEmpAttendDingdingAddParam) {
        hrEmpAttendDingdingService.add(hrEmpAttendDingdingAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑员工月度考勤(钉钉)
     *
     * @author pzh
     * @date  2025/02/12 15:17
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑员工月度考勤(钉钉)")
    @CommonLog("编辑员工月度考勤(钉钉)")
//    @SaCheckPermission("/sys/emp/edit")
    @PostMapping("/sys/emp/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrEmpAttendDingdingEditParam hrEmpAttendDingdingEditParam) {
        hrEmpAttendDingdingService.edit(hrEmpAttendDingdingEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除员工月度考勤(钉钉)
     *
     * @author pzh
     * @date  2025/02/12 15:17
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除员工月度考勤(钉钉)")
    @CommonLog("删除员工月度考勤(钉钉)")
//    @SaCheckPermission("/sys/emp/delete")
    @PostMapping("/sys/emp/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrEmpAttendDingdingIdParam> hrEmpAttendDingdingIdParamList) {
        hrEmpAttendDingdingService.delete(hrEmpAttendDingdingIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取员工月度考勤(钉钉)详情
     *
     * @author pzh
     * @date  2025/02/12 15:17
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取员工月度考勤(钉钉)详情")
//    @SaCheckPermission("/sys/emp/detail")
    @GetMapping("/sys/emp/detail")
    public CommonResult<HrEmpAttendDingding> detail(@Valid HrEmpAttendDingdingIdParam hrEmpAttendDingdingIdParam) {
        return CommonResult.data(hrEmpAttendDingdingService.detail(hrEmpAttendDingdingIdParam));
    }

    /**
     * 设置应计薪天数
     *
     * @author pzh
     * @date  2025/03/06 11:48
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("设置应计薪天数")
//    @SaCheckPermission("/sys/emp/setRequiredDay")
    @PostMapping("/sys/emp/setRequiredDay")
    public CommonResult<String> setRequiredDay(@RequestBody @Valid HrEmpAttendDingdingRequiredDayParam hrEmpAttendDingdingRequiredDayParam) {
        hrEmpAttendDingdingService.setRequiredDay(hrEmpAttendDingdingRequiredDayParam);
        return CommonResult.ok();
    }
}
