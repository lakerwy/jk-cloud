package vip.xiaonuo.oa.modular.attendance.holiday.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.oa.modular.attendance.holiday.entity.HolidayBalance;
import vip.xiaonuo.oa.modular.attendance.holiday.param.*;
import vip.xiaonuo.oa.modular.attendance.holiday.service.HolidayBalanceService;
import vip.xiaonuo.oa.modular.attendance.holiday.entity.HolidayRule;
import vip.xiaonuo.oa.modular.attendance.holiday.service.HolidayRuleService;
import vip.xiaonuo.oa.modular.attendance.holiday.vo.HolidayVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 公司员工假期数据表控制器
 *
 * @author mayike
 * @date  2024/07/04 16:46
 */
@Api(tags = "假期管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HolidayController {

    @Resource
    private HolidayRuleService holidayRuleService;

    @Resource
    private HolidayBalanceService holidayBalanceService;

    /**
     * 获取假期类型配置表分页
     *
     * @author mayike
     * @date  2024/07/05 08:33
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取假期类型配置表分页")
//    @SaCheckPermission("/oa/holidayRule/page")
    @GetMapping("/oa/holidayRule/page")
    public CommonResult<Page<HolidayRule>> holidayRulePage(HolidayRulePageParam holidayRulePageParam) {
        return CommonResult.data(holidayRuleService.page(holidayRulePageParam));
    }

    /**
     * 添加假期类型配置表
     *
     * @author mayike
     * @date  2024/07/05 08:33
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加假期类型配置表")
    @CommonLog("添加假期类型配置表")
//    @SaCheckPermission("/oa/holidayRule/add")
    @PostMapping("/oa/holidayRule/add")
    public CommonResult<String> holidayRuleAdd(@RequestBody @Valid HolidayRuleAddParam holidayRuleAddParam) {
        holidayRuleService.add(holidayRuleAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑假期类型配置表
     *
     * @author mayike
     * @date  2024/07/05 08:33
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑假期类型配置表")
    @CommonLog("编辑假期类型配置表")
//    @SaCheckPermission("/oa/holidayRule/edit")
    @PostMapping("/oa/holidayRule/edit")
    public CommonResult<String> holidayRuleEdit(@RequestBody @Valid HolidayRuleEditParam holidayRuleEditParam) {
        holidayRuleService.edit(holidayRuleEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除假期类型配置表
     *
     * @author mayike
     * @date  2024/07/05 08:33
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除假期类型配置表")
    @CommonLog("删除假期类型配置表")
//    @SaCheckPermission("/oa/holidayRule/delete")
    @PostMapping("/oa/holidayRule/delete")
    public CommonResult<String> holidayRuleDelete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                      CommonValidList<HolidayRuleIdParam> holidayRuleIdParamList) {
        holidayRuleService.delete(holidayRuleIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取假期类型配置表详情
     *
     * @author mayike
     * @date  2024/07/05 08:33
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取假期类型配置表详情")
//    @SaCheckPermission("/oa/holidayRule/detail")
    @GetMapping("/oa/holidayRule/detail")
    public CommonResult<HolidayRule> detail(@Valid HolidayRuleIdParam holidayRuleIdParam) {
        return CommonResult.data(holidayRuleService.detail(holidayRuleIdParam));
    }


    /**
     * 获取假期余额管理表分页
     *
     * @author mayike
     * @date  2024/07/05 10:57
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取假期余额管理表分页")
//    @SaCheckPermission("/oa/HolidayBalance/page")
    @GetMapping("/oa/HolidayBalance/page")
    public CommonResult<Page<HolidayVo>> page(HolidayBalancePageParam holidayBalancePageParam) {
        return CommonResult.data(holidayBalanceService.page(holidayBalancePageParam));
    }

    /**
     * 添加假期余额管理表
     *
     * @author mayike
     * @date  2024/07/05 10:57
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("添加假期余额管理表")
    @CommonLog("添加假期余额管理表")
    @ApiIgnore
//    @SaCheckPermission("/oa/HolidayBalance/add")
    @PostMapping("/oa/HolidayBalance/add")
    public CommonResult<String> add(@RequestBody @Valid HolidayBalanceAddParam holidayBalanceAddParam) {
        holidayBalanceService.add(holidayBalanceAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑假期余额管理表
     *
     * @author mayike
     * @date  2024/07/05 10:57
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("编辑假期余额管理表")
    @CommonLog("编辑假期余额管理表")
//    @SaCheckPermission("/oa/HolidayBalance/edit")
    @PostMapping("/oa/HolidayBalance/edit")
    public CommonResult<String> edit(@RequestBody @Valid HolidayBalanceEditParam holidayBalanceEditParam) {
        holidayBalanceService.edit(holidayBalanceEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除假期余额管理表
     *
     * @author mayike
     * @date  2024/07/05 10:57
     */
    @ApiOperationSupport(order = 13)
    @ApiOperation("删除假期余额管理表")
    @CommonLog("删除假期余额管理表")
    @ApiIgnore
//    @SaCheckPermission("/oa/HolidayBalance/delete")
    @PostMapping("/oa/HolidayBalance/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<HolidayBalanceIdParam> holidayBalanceIdParamList) {
        holidayBalanceService.delete(holidayBalanceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取假期余额管理表详情
     *
     * @author mayike
     * @date  2024/07/05 10:57
     */
    @ApiOperationSupport(order = 14)
    @ApiOperation("获取假期余额管理表详情")
//    @SaCheckPermission("/oa/HolidayBalance/detail")
    @GetMapping("/oa/HolidayBalance/detail")
    public CommonResult<HolidayBalance> detail(@Valid HolidayBalanceIdParam holidayBalanceIdParam) {
        return CommonResult.data(holidayBalanceService.detail(holidayBalanceIdParam));
    }

    /**
     * 初始化某个用户的假期
     * @param userId
     * @return
     */

    @GetMapping("/oa/Holiday/init")
    public CommonResult<String> init(@RequestParam(value = "userId") String userId ) {
        DateTime date = DateUtil.date();
        int year = date.year();
        holidayRuleService.initUserHolidayData(year,userId);
        return CommonResult.ok();
    }

    @PostMapping("/oa/Holiday/deleteByIds")
    public CommonResult<String> deleteByIds(@RequestBody List<String> ids){
        holidayBalanceService.deleteByIds(ids);
        return CommonResult.ok();
    }

}
