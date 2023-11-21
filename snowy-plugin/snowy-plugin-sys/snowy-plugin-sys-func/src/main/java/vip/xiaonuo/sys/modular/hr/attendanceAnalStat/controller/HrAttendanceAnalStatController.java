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
package vip.xiaonuo.sys.modular.hr.attendanceAnalStat.controller;

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
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.entity.HrAttendanceAnalStat;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatAddParam;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatEditParam;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatIdParam;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatPageParam;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.service.HrAttendanceAnalStatService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 考勤-员工考勤统计分析数据汇总表控制器
 *
 * @author ljy
 * @date  2023/11/21 23:04
 */
@Api(tags = "考勤-员工考勤统计分析数据汇总表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrAttendanceAnalStatController {

    @Resource
    private HrAttendanceAnalStatService hrAttendanceAnalStatService;

    /**
     * 获取考勤-员工考勤统计分析数据汇总表分页
     *
     * @author ljy
     * @date  2023/11/21 23:04
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取考勤-员工考勤统计分析数据汇总表分页")
    @SaCheckPermission("/sys/attendanceAnalStat/page")
    @GetMapping("/sys/attendanceAnalStat/page")
    public CommonResult<Page<HrAttendanceAnalStat>> page(HrAttendanceAnalStatPageParam hrAttendanceAnalStatPageParam) {
        return CommonResult.data(hrAttendanceAnalStatService.page(hrAttendanceAnalStatPageParam));
    }

    /**
     * 添加考勤-员工考勤统计分析数据汇总表
     *
     * @author ljy
     * @date  2023/11/21 23:04
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加考勤-员工考勤统计分析数据汇总表")
    @CommonLog("添加考勤-员工考勤统计分析数据汇总表")
    @SaCheckPermission("/sys/attendanceAnalStat/add")
    @PostMapping("/sys/attendanceAnalStat/add")
    public CommonResult<String> add(@RequestBody @Valid HrAttendanceAnalStatAddParam hrAttendanceAnalStatAddParam) {
        hrAttendanceAnalStatService.add(hrAttendanceAnalStatAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤-员工考勤统计分析数据汇总表
     *
     * @author ljy
     * @date  2023/11/21 23:04
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑考勤-员工考勤统计分析数据汇总表")
    @CommonLog("编辑考勤-员工考勤统计分析数据汇总表")
    @SaCheckPermission("/sys/attendanceAnalStat/edit")
    @PostMapping("/sys/attendanceAnalStat/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrAttendanceAnalStatEditParam hrAttendanceAnalStatEditParam) {
        hrAttendanceAnalStatService.edit(hrAttendanceAnalStatEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除考勤-员工考勤统计分析数据汇总表
     *
     * @author ljy
     * @date  2023/11/21 23:04
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除考勤-员工考勤统计分析数据汇总表")
    @CommonLog("删除考勤-员工考勤统计分析数据汇总表")
    @SaCheckPermission("/sys/attendanceAnalStat/delete")
    @PostMapping("/sys/attendanceAnalStat/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrAttendanceAnalStatIdParam> hrAttendanceAnalStatIdParamList) {
        hrAttendanceAnalStatService.delete(hrAttendanceAnalStatIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取考勤-员工考勤统计分析数据汇总表详情
     *
     * @author ljy
     * @date  2023/11/21 23:04
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取考勤-员工考勤统计分析数据汇总表详情")
    @SaCheckPermission("/sys/attendanceAnalStat/detail")
    @GetMapping("/sys/attendanceAnalStat/detail")
    public CommonResult<HrAttendanceAnalStat> detail(@Valid HrAttendanceAnalStatIdParam hrAttendanceAnalStatIdParam) {
        return CommonResult.data(hrAttendanceAnalStatService.detail(hrAttendanceAnalStatIdParam));
    }
}
