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
package vip.xiaonuo.sys.modular.hr.salary.controller;

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
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAttendPageParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.EmpSalaryParam;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryAttendService;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryAttendVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工资系统-月度考勤表控制器
 *
 * @author yong.ye
 * @date  2024/02/26 09:26
 */
@Api(tags = "工资系统-月度考勤表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SalaryAttendController {

    @Resource
    private SalaryAttendService salaryAttendService;

    /**
     * 获取工资系统-月度考勤表分页
     *
     * @author yong.ye
     * @date  2024/02/26 09:26
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取工资系统-月度考勤表分页")
//    @SaCheckPermission("/sys/hr/salary/attend/page")
    @GetMapping("/sys/hr/salary/attend/page")
    public CommonResult<Page<SalaryAttendVo>> page(SalaryAttendPageParam salaryAttendPageParam) {
        return CommonResult.data(salaryAttendService.page(salaryAttendPageParam));
    }

    /**
     * 添加工资系统-月度考勤表
     *
     * @author yong.ye
     * @date  2024/02/26 09:26
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加工资系统-月度考勤表")
    @CommonLog("添加工资系统-月度考勤表")
//    @SaCheckPermission("/sys/hr/salary/attend/add")
    @PostMapping("/sys/hr/salary/attend/add")
    public CommonResult<String> add(@RequestBody @Valid SalaryAttendAddParam salaryAttendAddParam) {
        salaryAttendService.add(salaryAttendAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑工资系统-月度考勤表
     *
     * @author yong.ye
     * @date  2024/02/26 09:26
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑工资系统-月度考勤表")
    @CommonLog("编辑工资系统-月度考勤表")
//    @SaCheckPermission("/sys/hr/salary/attend/edit")
    @PostMapping("/sys/hr/salary/attend/edit")
    public CommonResult<String> edit(@RequestBody @Valid SalaryAttendEditParam salaryAttendEditParam) {
        salaryAttendService.edit(salaryAttendEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除工资系统-月度考勤表
     *
     * @author yong.ye
     * @date  2024/02/26 09:26
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除工资系统-月度考勤表")
    @CommonLog("删除工资系统-月度考勤表")
//    @SaCheckPermission("/sys/hr/salary/attend/delete")
    @PostMapping("/sys/hr/salary/attend/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SalaryAttendIdParam> salaryAttendIdParamList) {
        salaryAttendService.delete(salaryAttendIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工资系统-月度考勤表详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:26
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取工资系统-月度考勤表详情")
//    @SaCheckPermission("/sys/hr/salary/attend/detail")
    @GetMapping("/sys/hr/salary/attend/detail")
    public CommonResult<SalaryAttendVo> detail(@Valid SalaryAttendIdParam salaryAttendIdParam) {
        return CommonResult.data(salaryAttendService.detail(salaryAttendIdParam));
    }


    /**
     * 获取工资系统-月度考勤数据【个人】
     *
     * @author yong.ye
     * @date  2024/02/26 09:26
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取工资系统-月度考勤数据【个人】")
    @GetMapping("/sys/hr/salary/attend/empAttend")
    public CommonResult<List<SalaryAttendVo>> empAttend(EmpSalaryParam empParam) {
        return CommonResult.data(salaryAttendService.empAttend(empParam));
    }
}
