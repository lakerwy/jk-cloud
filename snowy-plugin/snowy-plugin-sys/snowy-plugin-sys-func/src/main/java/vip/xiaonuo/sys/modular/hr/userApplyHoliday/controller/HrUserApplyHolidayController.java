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
package vip.xiaonuo.sys.modular.hr.userApplyHoliday.controller;

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
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.entity.HrUserApplyHoliday;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.param.*;
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.service.HrUserApplyHolidayService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 请假申请表控制器
 *
 * @author ljy
 * @date  2023/09/22 17:05
 */
@Api(tags = "请假申请表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrUserApplyHolidayController {

    @Resource
    private HrUserApplyHolidayService hrUserApplyHolidayService;

    /**
     * 获取请假申请表分页
     *
     * @author ljy
     * @date  2023/09/22 17:05
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取请假申请表分页")
    //@SaCheckPermission("/sys/userApplyHoliday/page")
    @GetMapping("/sys/userApplyHoliday/page")
    public CommonResult<Page<HrUserApplyHoliday>> page(HrUserApplyHolidayPageParam hrUserApplyHolidayPageParam) {
        return CommonResult.data(hrUserApplyHolidayService.page(hrUserApplyHolidayPageParam));
    }

    /**
     * 添加请假申请表
     *
     * @author ljy
     * @date  2023/09/22 17:05
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加请假申请表")
    @CommonLog("添加请假申请表")
    //@SaCheckPermission("/sys/userApplyHoliday/add")
    @PostMapping("/sys/userApplyHoliday/add")
    public CommonResult<String> add(@RequestBody @Valid HrUserApplyHolidayAddParam hrUserApplyHolidayAddParam) {
        return CommonResult.data(hrUserApplyHolidayService.add(hrUserApplyHolidayAddParam));
    }

    /**
     * 编辑请假申请表
     *
     * @author ljy
     * @date  2023/09/22 17:05
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑请假申请表")
    @CommonLog("编辑请假申请表")
    //@SaCheckPermission("/sys/userApplyHoliday/edit")
    @PostMapping("/sys/userApplyHoliday/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrUserApplyHolidayEditParam hrUserApplyHolidayEditParam) {
        hrUserApplyHolidayService.edit(hrUserApplyHolidayEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除请假申请表
     *
     * @author ljy
     * @date  2023/09/22 17:05
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除请假申请表")
    @CommonLog("删除请假申请表")
    //@SaCheckPermission("/sys/userApplyHoliday/delete")
    @PostMapping("/sys/userApplyHoliday/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrUserApplyHolidayIdParam> hrUserApplyHolidayIdParamList) {
        hrUserApplyHolidayService.delete(hrUserApplyHolidayIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取请假申请表详情
     *
     * @author ljy
     * @date  2023/09/22 17:05
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取请假申请表详情")
    //@SaCheckPermission("/sys/userApplyHoliday/detail")
    @GetMapping("/sys/userApplyHoliday/detail")
    public CommonResult<HrUserApplyHoliday> detail(@Valid HrUserApplyHolidayIdParam hrUserApplyHolidayIdParam) {
        return CommonResult.data(hrUserApplyHolidayService.detail(hrUserApplyHolidayIdParam));
    }

    /**
     * 计算时间差
     *
     * @author ljy
     * @date  2023/09/22 17:05
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("计算时间差")
    //@SaCheckPermission("/sys/userApplyHoliday/detail")
    @GetMapping("/sys/userApplyHoliday/calculateDay")
    public CommonResult<Double> calculateDay(@Valid CalculateDayParam calculateDayParam) {
        return CommonResult.data(hrUserApplyHolidayService.calculate(calculateDayParam));
    }


}
